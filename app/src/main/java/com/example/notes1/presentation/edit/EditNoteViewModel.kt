package com.example.notes1.presentation.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes1.domain.model.Note
import com.example.notes1.domain.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditNoteViewModel(
    private val repository: NoteRepository,
    private val noteId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditNoteUiState())
    val uiState: StateFlow<EditNoteUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getNote(noteId).collect { note ->
                if (note == null) {
                    _uiState.update { it.copy(loading = false, notFound = true) }
                } else {
                    _uiState.update {
                        it.copy(
                            id = note.id,
                            title = note.title,
                            content = note.content,
                            color = note.color,
                            loading = false,
                            notFound = false,
                        )
                    }
                }
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        _uiState.update { it.copy(title = newTitle) }
    }

    fun onContentChange(newContent: String) {
        _uiState.update { it.copy(content = newContent) }
    }

    fun saveNote() {
        val state = _uiState.value
        if (state.id.isBlank()) return
        viewModelScope.launch {
            repository.update(
                Note(
                    id = state.id,
                    title = state.title.trim(),
                    content = state.content.trim(),
                    date = System.currentTimeMillis(),
                    color = state.color
                )
            )
        }
    }

    fun deleteNote() {
        val id = _uiState.value.id
        if (id.isBlank()) return
        viewModelScope.launch {
            repository.delete(id)
        }
    }
}
