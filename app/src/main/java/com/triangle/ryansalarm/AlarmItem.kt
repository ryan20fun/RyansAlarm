package com.triangle.ryansalarm

import java.time.LocalDateTime

data class AlarmItem
	(
	val Time: LocalDateTime,
	val Message: String,
	val ID: Int
)