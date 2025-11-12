package com.example.notes1.domain.repository

import com.example.notes1.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>
    fun getNote(id: String): Flow<Note?>
    suspend fun add(note: Note)
    suspend fun update(note: Note)
    suspend fun delete(id: String)
}
