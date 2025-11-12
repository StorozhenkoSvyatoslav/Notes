package com.example.notes1.domain.model

import java.util.UUID

val DEFAULT_NOTE_COLOR: Int = 0xFF91F48F.toInt()

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val date: Long, // epochMillis
    val color: Int = DEFAULT_NOTE_COLOR,
)
