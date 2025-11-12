package com.example.notes1.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.notes1.R
import com.example.notes1.di.AppModule
import com.example.notes1.presentation.add.AddNoteViewModel
import com.example.notes1.presentation.common.viewModelFactory
import com.example.notes1.ui.theme.orange

@Composable
fun AddNoteScreen(navController: NavHostController) {
    val vm: AddNoteViewModel = viewModel(
        factory = viewModelFactory { AddNoteViewModel(AppModule.noteRepository) }
    )
    val state by vm.uiState.collectAsState()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .padding(horizontal = 32.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        painter = painterResource(R.drawable.arrow_back_24px),
                        contentDescription = null,
                        tint = orange,
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f),
                    text = "Add Note",
                    fontSize = 24.dp.value.sp,
                    fontWeight = FontWeight.W500,
                )

                IconButton(
                    onClick = {
                        vm.saveNote()
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        imageVector = Icons.Filled.Done,
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(top = 32.dp, start = 32.dp, end = 32.dp)
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.title,
                onValueChange = vm::onTitleChange,
                placeholder = { Text(text = "Title", fontSize = 24.sp, fontWeight = W400) },
                colors = TextFieldDefaults.colors(),
                singleLine = true
            )
            TextField(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                value = state.content,
                onValueChange = vm::onContentChange,
                placeholder = { Text(text = "Type something...", fontSize = 18.sp, fontWeight = W400) },
                colors = TextFieldDefaults.colors(),
            )
        }
    }
}