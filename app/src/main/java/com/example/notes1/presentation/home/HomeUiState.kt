package com.example.notes1.presentation.home

import com.example.notes1.domain.model.Note

data class HomeUiState(
    val isGrid: Boolean = false,
    val isFilterVisible: Boolean = false,
    val selectedFilterColor: Int? = null,
    val notes: List<Note> = emptyList(),
    val hasAnyNotes: Boolean = false
)
