package com.example.notes1.data.repository

import com.example.notes1.data.local.dao.NoteDao
import com.example.notes1.data.mapper.toDomain
import com.example.notes1.data.mapper.toEntity
import com.example.notes1.domain.model.Note
import com.example.notes1.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomNoteRepository(
    private val dao: NoteDao
) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> =
        dao.observeNotes().map { list -> list.map { it.toDomain() } }

    override fun getNote(id: String): Flow<Note?> =
        dao.observeById(id).map { it?.toDomain() }

    override suspend fun add(note: Note) {
        dao.insert(note.toEntity())
    }

    override suspend fun update(note: Note) {
        dao.update(note.toEntity())
    }

    override suspend fun delete(id: String) {
        dao.deleteById(id)
    }
}
