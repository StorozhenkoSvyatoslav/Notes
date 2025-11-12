package com.example.notes1.view.navigation

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Filter : Routes("filter")
    object Add : Routes("add")
    object Edit : Routes("edit/{id}") {
        fun build(id: String) = "edit/$id"
        const val ARG_ID = "id"
    }
}