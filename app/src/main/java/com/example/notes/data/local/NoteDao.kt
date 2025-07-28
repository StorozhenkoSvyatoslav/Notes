package com.example.notes.data.local

import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    suspend fun getNotes(): List<NoteEntity>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity)

    @Delete
    suspend fun delete(note: NoteEntity)
}