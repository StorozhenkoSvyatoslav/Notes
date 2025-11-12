package com.example.notes1.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes1.domain.model.Note
import com.example.notes1.domain.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var allNotes: List<Note> = emptyList()

    init {
        // Observe notes
        viewModelScope.launch {
            repository.getNotes().collect { notes ->
                allNotes = notes
                applyFilter()
            }
        }
    }

    private fun applyFilter() {
        val filteredNotes = if (_uiState.value.selectedFilterColor != null) {
            allNotes.filter { it.color == _uiState.value.selectedFilterColor }
        } else {
            allNotes
        }
        _uiState.update {
            it.copy(
                notes = filteredNotes,
                hasAnyNotes = allNotes.isNotEmpty()
            )
        }
    }

    fun toggleGrid() {
        _uiState.update { it.copy(isGrid = !it.isGrid) }
    }

    fun toggleFilter() {
        _uiState.update { it.copy(isFilterVisible = !it.isFilterVisible) }
    }

    fun setColorFilter(color: Int) {
        _uiState.update { it.copy(selectedFilterColor = color) }
        applyFilter()
    }

    fun resetFilter() {
        _uiState.update { it.copy(selectedFilterColor = null) }
        applyFilter()
    }

    fun deleteNote(id: String) {
        viewModelScope.launch { repository.delete(id) }
    }

    fun updateNoteColor(note: Note, color: Int) {
        viewModelScope.launch {
            repository.update(note.copy(color = color, date = System.currentTimeMillis()))
        }
    }
}
