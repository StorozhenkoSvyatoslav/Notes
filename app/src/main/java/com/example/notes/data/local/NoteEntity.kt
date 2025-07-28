package com.example.notes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notes.domain.model.Note

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long
)

fun NoteEntity.toNote(): Note = Note(id, title, content, timestamp)
fun Note.toEntity(): NoteEntity = NoteEntity(id, title, content, timestamp)
