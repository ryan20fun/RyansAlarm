package com.triangle.ryansalarm

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TimePicker
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.properties.Delegates

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

	private var alarmID by Delegates.notNull<Int>()
	private lateinit var timePicker: TimePicker
	private lateinit var scheduler: AndroidAlarmScheduler
	private var alarmItem: AlarmItem? = null
	private lateinit var layoutRelativeInput: LinearLayout
	private lateinit var useAbsoluteTime: SwitchCompat
	private lateinit var relativeHour: NumberPicker
	private lateinit var relativeMinute: NumberPicker

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		arguments?.let {
			param1 = it.getString(ARG_PARAM1)
			param2 = it.getString(ARG_PARAM2)
		}

		alarmID = 500
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

		this.layoutRelativeInput = view.findViewById(R.id.alarm_layout_Spinner)

		//region Time picker
		this.timePicker = view.findViewById(R.id.alarm_timePicker)
		this.timePicker.setIs24HourView(true)
		//endregion

		//region relative time
		this.relativeHour = view.findViewById(R.id.alarm_hour)
		this.relativeMinute = view.findViewById(R.id.alarm_minute)

		this.relativeHour.minValue = 0
		this.relativeHour.maxValue = 24
		this.relativeMinute.minValue = 0
		this.relativeMinute.maxValue = 60
		//endregion

		//region Buttons
		view.findViewById<Button>(R.id.alarm_button_start)
			.setOnClickListener {
				start()
			}
		view.findViewById<Button>(R.id.alarm_button_stop)
			.setOnClickListener { stop() }
		//endregion

		//region Relative switch
		this.useAbsoluteTime = view.findViewById(R.id.alarm_use_absolute_time)
		this.useAbsoluteTime.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
			if (isChecked)
			{
				this.layoutRelativeInput.visibility = View.GONE
				this.timePicker.visibility = View.VISIBLE
			}
			else
			{
				this.layoutRelativeInput.visibility = View.VISIBLE
				this.timePicker.visibility = View.GONE
			}
		}
		useAbsoluteTime.isChecked = true
		//endregion
	}

	private fun start()
	{
		var datePart: LocalDate = LocalDate.now()
		lateinit var timePart: LocalTime
		lateinit var msg: String

		if (this.useAbsoluteTime.isChecked)
		{
			msg = "Absolute"
			timePart = LocalTime.of(this.timePicker.hour, this.timePicker.minute)

			//region Adjust date to tomorrow if the time is before now
			val nowTime = LocalTime.now()
			if (nowTime.isBefore(timePart))
				datePart = datePart.plusDays(1)

			if (nowTime.hour > timePart.hour
				|| (nowTime.hour == timePart.hour && nowTime.minute > timePart.minute)
			)
				datePart = datePart.plusDays(1)
			//endregion
		}
		else
		{
			msg = "Relative"
			timePart = LocalTime.now()
			timePart = timePart.plusHours(this.relativeHour.value.toLong())
			timePart = timePart.plusMinutes(this.relativeMinute.value.toLong())
		}

		val dateTime = LocalDateTime.of(datePart, timePart)
		this.alarmItem = AlarmItem(dateTime, msg, this.alarmID)
		this.alarmItem?.let(scheduler::Schedule)
		Log.d(
			"AlarmFragment",
			"Alarm scheduled for: ${
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(dateTime)
			}"
		)
	}

	private fun stop()
	{
		this.alarmItem?.let(scheduler::Cancel)
		MediaSingleton.stop()
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