package com.triangle.ryansalarm

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerFragment : Fragment()
{
	private val PV_Colour: String by lazy {
		requireArguments().getString("colour") ?: "#E4E4E4"
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)

		view.findViewById<ConstraintLayout>(R.id.root)
			.setBackgroundColor(Color.parseColor(PV_Colour))
	}
}

class MyAdapter(activity: AppCompatActivity, private val fragmentData: List<FragmentData>) :
	FragmentStateAdapter(activity)
{
	val fragments: MutableMap<Int, Fragment> = mutableMapOf()

	override fun getItemCount(): Int
	{
		return fragmentData.size
	}

	@Throws(RuntimeException::class)
	override fun createFragment(position: Int): Fragment
	{
		val fragment = when (position)
		{
			0 -> ViewPagerFragment().apply {
				arguments = Bundle().apply { putString("colour", fragmentData[position].Colour) }
			}

			1 -> AlarmFragment().apply {
				arguments = Bundle().apply { putString("colour", fragmentData[position].Colour) }
			}

			2 -> AlarmTriggeredFragment.newInstance(-1, "")

			else -> throw NotImplementedError()
		}

		this.fragments[position] = fragment
		return fragment
	}
}