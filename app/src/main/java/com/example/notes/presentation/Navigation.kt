package com.example.notes.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notes.presentation.add_edit_note.AddEditNoteScreen
import com.example.notes.presentation.note_list.NoteListScreen

object Routes {
    const val NOTE_LIST = "note_list"
    const val ADD_EDIT_NOTE = "add_edit_note"
}

@Composable
fun NotesNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.NOTE_LIST,
        modifier = modifier
    ) {
        composable(Routes.NOTE_LIST) {
            NoteListScreen(
                onNoteClick = { note ->
                    navController.navigate("${Routes.ADD_EDIT_NOTE}?noteId=${note.id ?: -1}")
                }
            )
        }
        composable(
            route = "${Routes.ADD_EDIT_NOTE}?noteId={noteId}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditNoteScreen(
                onSaveNote = {
                    navController.popBackStack()
                }
            )
        }
    }
}