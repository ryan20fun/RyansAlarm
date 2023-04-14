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

		binding.viewPager.adapter = MyAdapter(this, fragmentData)
		mediator.attach()

		// TODO: select the alarm tab temporarily
		var tab = binding.tabs.getTabAt(1)
		binding.tabs.selectTab(tab)

		// TODO: URI can be null
		val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
		assert(null != uri)
		MediaSingleton.initialise(
			this,
			uri
		)

		// switch to the alarm triggered fragment
		MediaSingleton.listenerActivity = {
			var tab = binding.tabs.getTabAt(2)
			binding.tabs.selectTab(tab)

			// TODO: update tab data

			Log.d("MainActivity", "Switched to alarm triggered tab fragment")
		}
	}

	override fun onDestroy()
	{
		super.onDestroy()

		MediaSingleton.cleanup()
	}
}