package com.triangle.ryansalarm

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

private const val ARG_PARAM_ALARM_ID = "alarm_id"
private const val ARG_PARAM_ALARM_MSG = "alarm_msg"

/**
 * A simple [Fragment] subclass.
 * Use the [AlarmTriggeredFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlarmTriggeredFragment : Fragment()
{
	private var alarmID: Int? = null
	private var alarmMessage: String? = null

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		arguments?.let {
			alarmID = it.getInt(ARG_PARAM_ALARM_ID)
			alarmMessage = it.getString(ARG_PARAM_ALARM_MSG)
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View?
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_alarm_triggered, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)

		view.findViewById<TextView>(R.id.alarmtriggered_Message).text = this.alarmMessage ?: ""

		//region Buttons
		view.findViewById<Button>(R.id.alarmtriggered_CancelButton)
			.setOnClickListener {
				cancelAlarm()
			}
		view.findViewById<Button>(R.id.alarmtriggered_SnoozeButton)
			.setOnClickListener {
				snoozeAlarm()
			}
		//endregion
	}

	private fun cancelAlarm()
	{
		Log.d("AlarmTriggeredFragment", "Alarm $alarmID cancelled")
	}

	private fun snoozeAlarm()
	{
		Log.d("AlarmTriggeredFragment", "Alarm $alarmID snoozed")
	}

	companion object
	{
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @param alarmID Parameter 1.
		 * @param alarmMessage Parameter 2.
		 * @return A new instance of fragment AlarmTriggeredFragment.
		 */
		@JvmStatic
		fun newInstance(alarmID: Int, alarmMessage: String) =
			AlarmTriggeredFragment().apply {
				arguments = Bundle().apply {
					putInt(ARG_PARAM_ALARM_ID, alarmID)
					putString(ARG_PARAM_ALARM_MSG, alarmMessage)
				}
			}
	}
}