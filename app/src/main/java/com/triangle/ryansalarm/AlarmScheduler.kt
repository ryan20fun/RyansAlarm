package com.triangle.ryansalarm

interface AlarmScheduler
{
	fun Schedule(Item: AlarmItem)
	fun Cancel(Item: AlarmItem)
	fun Cancel(AlarmID: Int)
}