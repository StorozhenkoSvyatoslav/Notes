package com.example.notes1.view.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notes1.view.AddNoteScreen
import com.example.notes1.view.EditNoteScreen
import com.example.notes1.view.HomeScreen

@Composable
fun MainApp(innerPadding: PaddingValues) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            HomeScreen(innerPadding, navController)
        }
        composable(Routes.Add.route) {
            AddNoteScreen(navController)
        }
        composable(
            route = Routes.Edit.route,
            arguments = listOf(navArgument(Routes.Edit.ARG_ID) { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(Routes.Edit.ARG_ID) ?: return@composable
            EditNoteScreen(navController, id)
        }
    }
}
