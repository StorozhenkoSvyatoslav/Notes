package com.example.notes1.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes1.domain.model.Note
import com.example.notes1.domain.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddNoteViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddNoteUiState())
    val uiState: StateFlow<AddNoteUiState> = _uiState.asStateFlow()

    fun onTitleChange(newTitle: String) {
        _uiState.update { it.copy(title = newTitle) }
    }

    fun onContentChange(newContent: String) {
        _uiState.update { it.copy(content = newContent) }
    }

    fun saveNote() {
        val state = _uiState.value
        if (state.title.isBlank() && state.content.isBlank()) return
        viewModelScope.launch {
            repository.add(
                Note(
                    title = state.title.trim(),
                    content = state.content.trim(),
                    date = System.currentTimeMillis()
                )
            )
        }
    }
}
