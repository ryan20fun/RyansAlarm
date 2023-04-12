package com.triangle.ryansalarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver : BroadcastReceiver()
{
	override fun onReceive(context: Context?, intent: Intent?)
	{
		val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
		val id = intent.getIntExtra("ALARM_ID", -1)

		Log.d( "AlarmReceiver","Alarm $id triggered: $message")
		MediaSingleton.play()
	}
}