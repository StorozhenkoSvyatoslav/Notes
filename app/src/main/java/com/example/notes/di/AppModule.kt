package com.example.notes.di

import android.app.Application
import androidx.room.Room
import com.example.notes.data.local.NoteDao
import com.example.notes.data.local.NoteDatabase
import com.example.notes.data.repository.NoteRepositoryImpl
import com.example.notes.domain.repository.NoteRepository
import com.example.notes.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase =
        Room.databaseBuilder(app, NoteDatabase::class.java, "notes_db").build()

    @Provides
    @Singleton
    fun provideNoteDao(db: NoteDatabase) = db.dao

    @Provides
    @Singleton
    fun provideNoteRepository(dao: NoteDao): NoteRepository = NoteRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository) = NoteUseCases(
        getNotes = GetNotes(repository),
        getNote = GetNote(repository),
        addNote = AddNote(repository),
        deleteNote = DeleteNote(repository)
    )
}