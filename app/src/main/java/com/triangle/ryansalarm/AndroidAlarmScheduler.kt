package com.triangle.ryansalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.time.ZoneId

class AndroidAlarmScheduler(private val context: Context) : AlarmScheduler
{
	private val alarm = context.getSystemService(AlarmManager::class.java)

	override fun Schedule(Item: AlarmItem)
	{
		val intent = Intent(context, AlarmReceiver::class.java).apply {
			putExtra("EXTRA_MESSAGE", Item.Message)
		}

		var milliSeconds = Item.Time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000;

		this.alarm.setExactAndAllowWhileIdle(
			AlarmManager.RTC_WAKEUP,
			milliSeconds,
			PendingIntent.getBroadcast(
				context,
				Item.ID,
				intent,
				PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
			)//use same request code to update
		)
	}

	override fun Cancel(Item: AlarmItem)
	{
		this.alarm.cancel(
			PendingIntent.getBroadcast(
				context,
				Item.ID,
				Intent(context, AlarmReceiver::class.java),
				PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
			)
		)
	}

}