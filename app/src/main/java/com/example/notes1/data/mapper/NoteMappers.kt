package com.example.notes1.data.mapper

import com.example.notes1.data.local.entity.NoteEntity
import com.example.notes1.domain.model.Note

fun NoteEntity.toDomain(): Note = Note(
    id = id,
    title = title,
    content = content,
    date = date,
    color = color,
)

fun Note.toEntity(): NoteEntity = NoteEntity(
    id = id,
    title = title,
    content = content,
    date = date,
    color = color,
)
