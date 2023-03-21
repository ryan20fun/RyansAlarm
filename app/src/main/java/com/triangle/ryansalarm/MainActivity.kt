package com.triangle.ryansalarm

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.triangle.ryansalarm.databinding.ActivityMainBinding

data class FragmentData( val Title: String, val Colour: String)

class MyAdapter(activity: MainActivity, private val fragmentData: List<FragmentData>)
	: FragmentStateAdapter(activity)
{
	override fun getItemCount(): Int
	{
		return fragmentData.size
	}

	@Throws(RuntimeException::class)
	override fun createFragment(position: Int): Fragment
	{
		return AlarmFragment().apply {
			arguments = Bundle().apply { putString("colour", fragmentData[position].Colour) }
		}
	}
}

class ViewPagerFragment : Fragment()
{
	private val PV_Colour: String by lazy {
		requireArguments().getString("colour") ?: "#E4E4E4"
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)

		view.findViewById<ConstraintLayout>(R.id.root).setBackgroundColor(Color.parseColor(PV_Colour))
	}
}

class MainActivity : AppCompatActivity()
{
	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		val fragmentData = listOf<FragmentData>(
			FragmentData("Alarm", "#AA0000"),
			FragmentData("Default", "#00AA00"),
			FragmentData("Settings", "#0000AA"),
		)

		val mediator = TabLayoutMediator(binding.tabs, binding.viewPager)
		{ tab, position ->
			tab.text = fragmentData[position].Title
		}

		binding.viewPager.adapter = MyAdapter(this, fragmentData)
		mediator.attach()
	}
}