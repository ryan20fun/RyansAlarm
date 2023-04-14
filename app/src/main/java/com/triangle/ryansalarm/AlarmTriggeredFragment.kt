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
	private var alarmID: Int = -1
	private var alarmMessage: String = ""
	private var viewMessage: TextView? = null

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		arguments?.let {
			/* Check the default value first.
			 * The properties have been set if they don't match.
			 */
			if (-1 == this.alarmID)
				alarmID = it.getInt(ARG_PARAM_ALARM_ID)

			if (this.alarmMessage.isEmpty())
				alarmMessage = it.getString(ARG_PARAM_ALARM_MSG) ?: ""
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

		this.viewMessage = view.findViewById(R.id.alarmtriggered_Message)
		setData(this.alarmID, this.alarmMessage)

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

	fun setData(id:Int, message:String)
	{
		this.alarmID = id
		this.alarmMessage = message

		/* Only set the message here if the view has been created,
		 * otherwise the on created event will set the message */
		this.viewMessage?.text = this.alarmMessage

		Log.d("AlarmTriggeredFragment", "Alarm $id data set to $message")
	}

	private fun cancelAlarm()
	{
		// TODO: implement
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