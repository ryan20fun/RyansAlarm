package com.triangle.ryansalarm

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import androidx.constraintlayout.widget.ConstraintLayout
import java.time.LocalDateTime

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AlarmFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlarmFragment : Fragment()
{
	// TODO: Rename and change types of parameters
	private var param1: String? = null
	private var param2: String? = null

	private val colour: String by lazy {
		requireArguments().getString("colour") ?: "#E4E4E4"
	}


	private lateinit var time: TimePicker
	private lateinit var scheduler: AndroidAlarmScheduler
	private var alarmItem: AlarmItem? = null

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		arguments?.let {
			param1 = it.getString(ARG_PARAM1)
			param2 = it.getString(ARG_PARAM2)
		}

		scheduler = AndroidAlarmScheduler(requireContext())
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View?
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_alarm, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)

		val layout = view.findViewById<ConstraintLayout>(R.id.root)
		layout?.setBackgroundColor(Color.parseColor(colour))

		this.time = view.findViewById(R.id.alarm_timePicker)
		this.time.setIs24HourView(true)

		view.findViewById<Button>(R.id.alarm_button_start)
			.setOnClickListener {
				start()
			}
		view.findViewById<Button>(R.id.alarm_button_stop)
			.setOnClickListener { stop() }
	}

	private fun start()
	{
		var time = LocalDateTime.now().plusSeconds(5)
		this.alarmItem = AlarmItem(time, "Hello Alarm", 500)
		this.alarmItem?.let ( scheduler::Schedule )
	}
	private fun stop()
	{
		this.alarmItem?.let ( scheduler::Cancel )
	}

	companion object
	{
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @param param1 Parameter 1.
		 * @param param2 Parameter 2.
		 * @return A new instance of fragment AlarmFragment.
		 */
		// TODO: Rename and change types and number of parameters
		@JvmStatic
		fun newInstance(param1: String, param2: String) =
			AlarmFragment().apply {
				arguments = Bundle().apply {
					putString(ARG_PARAM1, param1)
					putString(ARG_PARAM2, param2)
				}
			}
	}
}