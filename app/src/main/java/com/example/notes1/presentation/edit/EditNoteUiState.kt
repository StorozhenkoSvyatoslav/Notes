package com.example.notes1.presentation.edit

import com.example.notes1.domain.model.DEFAULT_NOTE_COLOR

data class EditNoteUiState(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val color: Int = DEFAULT_NOTE_COLOR,
    val loading: Boolean = true,
    val notFound: Boolean = false,
)
