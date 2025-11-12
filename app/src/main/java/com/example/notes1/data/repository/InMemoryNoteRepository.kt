package com.example.notes1.data.repository

import com.example.notes1.domain.model.Note
import com.example.notes1.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class InMemoryNoteRepository : NoteRepository {
    private val notesFlow = MutableStateFlow<List<Note>>(emptyList())

    override fun getNotes(): Flow<List<Note>> = notesFlow.asStateFlow()

    override fun getNote(id: String): Flow<Note?> =
        notesFlow.asStateFlow().map { list -> list.find { it.id == id } }

    override suspend fun add(note: Note) {
        notesFlow.value = notesFlow.value + note
    }

    override suspend fun update(note: Note) {
        notesFlow.value = notesFlow.value.map { if (it.id == note.id) note else it }
    }

    override suspend fun delete(id: String) {
        notesFlow.value = notesFlow.value.filterNot { it.id == id }
    }
}
