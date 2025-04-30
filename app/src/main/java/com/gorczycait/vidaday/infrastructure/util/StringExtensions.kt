package com.gorczycait.vidaday.infrastructure.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Suppress("TooGenericExceptionCaught", "SwallowedException")
fun String.formatTimestamp(): String = try {
    val dateTime = LocalDateTime.parse(this)
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
    dateTime.format(formatter)
} catch (e: Exception) {
    this
}
