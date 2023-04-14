package com.triangle.ryansalarm

import android.media.RingtoneManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.triangle.ryansalarm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
	private lateinit var binding: ActivityMainBinding
	private lateinit var mediator: TabLayoutMediator
	private lateinit var adapter: MyAdapter

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		val fragmentData = listOf(
			FragmentData("Entries", "#0000AA"),
			FragmentData("Alarm", "#AA0000"),
			FragmentData("Activated", "#00AA00"),
		)

		mediator = TabLayoutMediator(binding.tabs, binding.viewPager)
		{ tab, position ->
			tab.text = fragmentData[position].Title
		}

		this.adapter = MyAdapter(this, fragmentData)
		binding.viewPager.adapter = this.adapter
		mediator.attach()

		val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
		assert(null != uri)
		MediaSingleton.initialise(
			this,
			uri
		)

		// switch to the alarm triggered fragment
		MediaSingleton.listenerActivity = {
			val tab = binding.tabs.getTabAt(2)
			binding.tabs.selectTab(tab)

			val atf = this.adapter.fragments[2] as AlarmTriggeredFragment
			// TODO: get alarm data for this
			atf.setData(0, "Triggered")

			Log.d("MainActivity", "Switched to alarm triggered tab fragment")
		}
	}

	override fun onDestroy()
	{
		super.onDestroy()

		MediaSingleton.cleanup()
	}
}