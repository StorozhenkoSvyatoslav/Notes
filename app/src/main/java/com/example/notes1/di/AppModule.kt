package com.example.notes1.di

import android.content.Context
import androidx.room.Room
import com.example.notes1.data.local.db.AppDatabase
import com.example.notes1.data.repository.RoomNoteRepository
import com.example.notes1.domain.repository.NoteRepository

object AppModule {
    private lateinit var database: AppDatabase

    // Простейший локатор зависимостей без DI-фреймворка
    val noteRepository: NoteRepository by lazy { RoomNoteRepository(database.noteDao()) }

    fun init(context: Context) {
        if (!::database.isInitialized) {
            database = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "notes.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
