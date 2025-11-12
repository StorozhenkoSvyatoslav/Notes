package com.example.notes1.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.notes1.R
import com.example.notes1.di.AppModule
import com.example.notes1.presentation.common.viewModelFactory
import com.example.notes1.presentation.home.HomeViewModel
import com.example.notes1.domain.model.Note
import com.example.notes1.ui.theme.*
import com.example.notes1.view.navigation.Routes

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    navController: NavHostController,
) {
    val vm: HomeViewModel = viewModel(
        factory = viewModelFactory { HomeViewModel(AppModule.noteRepository) }
    )
    val state by vm.uiState.collectAsState()

    var openNoteId by remember { mutableStateOf<String?>(null) }

    Scaffold(
        Modifier.padding(innerPadding),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalAlignment = CenterVertically
            ) {
                Text(
                    "Notes",
                    fontSize = 36.dp.value.sp,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = { vm.toggleFilter() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.color_lens_black),
                        contentDescription = "Color Lens",
                        modifier = Modifier
                            .size(32.dp),
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))

                IconButton (
                    onClick = { vm.toggleGrid() },
                ) {
                    if (state.isGrid) {
                        Icon(
                            painter = painterResource(id = R.drawable.squares),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(32.dp),
                        )
                    }
                    else {
                        Icon(
                            painter = painterResource(id = R.drawable.stream_black_24dp),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(32.dp),
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.Add.route)
                },
                containerColor = orange,
                shape = androidx.compose.foundation.shape.CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                modifier = Modifier
                    .size(70.dp)
            ) {
                Icon(
                    Icons.Filled.Add,
                    modifier = Modifier.size(40.dp),
                    tint = Color.White,
                    contentDescription = null
                )
            }
        },
    ) {
        Column {
            if (state.isFilterVisible) {
                FilterScreen(
                    paddingValues = it,
                    selectedColor = state.selectedFilterColor,
                    onColorSelected = vm::setColorFilter,
                    onResetFilter = vm::resetFilter
                )
            }

            if (state.notes.isEmpty()) {
                Box(
                    Modifier.padding(
                        top = if (state.isFilterVisible) 0.dp else it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding(),
                        start = 20.dp,
                        end = 20.dp
                    ).fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        if (!state.hasAnyNotes) {
                            // Показываем когда заметок вообще нет
                            Icon(
                                painter = painterResource(id = R.drawable.negr),
                                contentDescription = "Empty Box",
                                modifier = Modifier
                                    .size(200.dp)
                            )
                            Text("Create your first note!", fontSize = 20.sp)
                        } else {
                            // Показываем когда заметки есть, но не подходят под фильтр
                            Text(
                                text = "No notes found for this filter",
                                fontSize = 18.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
            else {
                if (state.isGrid) {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2),
                        modifier = Modifier.padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = if (state.isFilterVisible) 0.dp else it.calculateTopPadding(),
                            bottom = it.calculateBottomPadding()
                        ).fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(state.notes) { note ->
                            ListItem(
                                note = note,
                                isMenuOpen = openNoteId == note.id,
                                onClick = { navController.navigate(Routes.Edit.build(note.id)) },
                                onLongPress = { openNoteId = note.id },
                                onDismissMenu = { if (openNoteId == note.id) openNoteId = null },
                                onDelete = {
                                    vm.deleteNote(note.id)
                                    if (openNoteId == note.id) openNoteId = null
                                },
                                onPickColor = { color ->
                                    vm.updateNoteColor(note, color.toArgb())
                                }
                            )
                        }
                    }
                }
                else {
                    LazyColumn(
                        modifier = Modifier.padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = if (state.isFilterVisible) 0.dp else it.calculateTopPadding(),
                            bottom = it.calculateBottomPadding()
                        ).fillMaxSize()
                    ) {
                        items(state.notes) { note ->
                            ListItem(
                                note = note,
                                isMenuOpen = openNoteId == note.id,
                                onClick = { navController.navigate(Routes.Edit.build(note.id)) },
                                onLongPress = { openNoteId = note.id },
                                onDismissMenu = { if (openNoteId == note.id) openNoteId = null },
                                onDelete = {
                                    vm.deleteNote(note.id)
                                    if (openNoteId == note.id) openNoteId = null
                                },
                                onPickColor = { color ->
                                    vm.updateNoteColor(note, color.toArgb())
                                }
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun ListItem(
    note: Note,
    isMenuOpen: Boolean,
    onClick: () -> Unit,
    onLongPress: () -> Unit,
    onDismissMenu: () -> Unit,
    onDelete: () -> Unit,
    onPickColor: (Color) -> Unit,
) {
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .background(Color(note.color), shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                .pointerInput(note.id) {
                    detectTapGestures(
                        onTap = { onClick() },
                        onLongPress = { onLongPress() }
                    )
                },
        ) {
            val displayTitle = note.title.ifBlank {
                val contentPreview = note.content.take(20)
                if (note.content.length > 20) {
                    "$contentPreview..."
                } else {
                    contentPreview
                }
            }
            Text(
                text = displayTitle,
                Modifier.padding(horizontal = 30.dp, vertical = 20.dp),
                fontSize = 24.sp
            )
        }

        DropdownMenu(
            expanded = isMenuOpen,
            onDismissRequest = onDismissMenu,
        ) {
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = null
                    )
                },
                text = { Text("Delete Note") },
                onClick = onDelete
            )
            HorizontalDivider()
            Text(
                text = "Pick color",
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
            )
            val cols = 3
            val palette = listOf(
                red, orange, yellow,
                green, blue, pink,
                violet, dark_violet, rose,
                greyWhite, greyDark
            )
            palette.chunked(cols).forEach { rowColors ->
                Row(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowColors.forEach { c ->
                        Box(
                            modifier = Modifier
                                .size(width = 36.dp, height = 24.dp)
                                .background(color = c, shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                                .clickable {
                                    onPickColor(c)
                                    onDismissMenu()
                                }
                        )
                    }
                }
            }
        }
    }
}