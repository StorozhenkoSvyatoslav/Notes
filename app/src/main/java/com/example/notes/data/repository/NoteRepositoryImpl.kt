package com.example.notes.data.repository

import com.example.notes.data.local.NoteDao
import com.example.notes.data.local.toEntity
import com.example.notes.data.local.toNote
import com.example.notes.domain.model.Note
import com.example.notes.domain.repository.NoteRepository

class NoteRepositoryImpl(private val dao: NoteDao) : NoteRepository {
    override suspend fun getNotes(): List<Note> = dao.getNotes().map { it.toNote() }
    override suspend fun getNoteById(id: Int): Note? = dao.getNoteById(id)?.toNote()
    override suspend fun insertNote(note: Note) = dao.insert(note.toEntity())
    override suspend fun deleteNote(note: Note) = dao.delete(note.toEntity())
}