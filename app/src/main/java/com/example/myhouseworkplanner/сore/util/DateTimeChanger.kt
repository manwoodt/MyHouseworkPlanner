package com.example.myhouseworkplanner.сore.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

// Превращаем LocalDateTime из UI в Long для базы (LocalDateTime не может там хранится)
fun LocalDateTime.toEpochMillis(): Long {
    return this.atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

fun Long.toLocalDateTime(): LocalDateTime {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
}

