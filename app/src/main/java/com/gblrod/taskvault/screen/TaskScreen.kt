package com.gblrod.taskvault.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gblrod.taskvault.R
import com.gblrod.taskvault.components.AlertDialogDeleteTask
import com.gblrod.taskvault.components.AlertDialogNewTask
import com.gblrod.taskvault.ui.theme.BackgroundColorOne
import com.gblrod.taskvault.ui.theme.BackgroundColorThree
import com.gblrod.taskvault.ui.theme.BackgroundColorTwo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(modifier: Modifier = Modifier) {
    var newTask by remember { mutableStateOf("") }
    val taskList = remember { mutableStateListOf<String>() }
    val focus = LocalFocusManager.current
    var selectedTask by remember { mutableStateOf<String?>(null) }
    var newTaskDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    newTask = ""
                    newTaskDialog = true
                },
                modifier = Modifier.padding(bottom = 24.dp),
                shape = CircleShape,
                containerColor = Color.LightGray
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Ícone de Adicionar nova task",
                    tint = Color.Black
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ) { data ->
                Snackbar(
                    snackbarData = data,
                    shape = RoundedCornerShape(12.dp)
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Minhas Tarefas (${taskList.size})",
                        color = Color.Black
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingScaffold ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                BackgroundColorOne,
                                BackgroundColorTwo,
                                BackgroundColorThree
                            )
                        )
                    )
                    .padding(horizontal = 16.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focus.clearFocus()
                        })
                    }
            )
            {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(paddingScaffold)
                    ) {
                        items(taskList) { task ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.LightGray
                                )
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = task,
                                        color = Color.Black,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(
                                            horizontal = 16.dp,
                                            vertical = 12.dp
                                        )
                                    )

                                    IconButton(onClick = {
                                        selectedTask = task
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Ícone de deletar task",
                                            tint = Color.Black
                                        )
                                    }
//                                IconButton(onClick = {
//                                    Toast.makeText(context, "Em breve", Toast.LENGTH_SHORT).show()
//                                }) {
//                                    Icon(
//                                        painter = painterResource(id = R.drawable.ic_keyboard_arrow_down),
//                                        contentDescription = "Ícone de expandir Card",
//                                        tint = Color.Black,
//                                        modifier = Modifier.size(30.dp)
//                                    )
//                                }
                                }
                            }
                        }
                    }

                    if (newTaskDialog) {
                        AlertDialogNewTask(
                            task = newTask,
                            onTaskChange = { newTask = it },
                            onDismissRequest = {
                                newTaskDialog = false
                            },
                            onConfirmation = {
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message = "Tarefa $newTask adicionada",
                                        duration = SnackbarDuration.Short,
                                        actionLabel = "Desfazer"
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        taskList.remove(newTask)
                                    }
                                }
                                taskList.add(newTask)
                                newTaskDialog = false
                            },
                            dialogTitle = "Adicionar Tarefa",
                        )
                    }

                    selectedTask?.let { taskToDelete ->
                        AlertDialogDeleteTask(
                            onDismissRequest = {
                                selectedTask = null
                            },
                            onConfirmation = {
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message = "Tarefa $taskToDelete deletada",
                                        duration = SnackbarDuration.Short,
                                        actionLabel = "Desfazer"
                                    )

                                    if (result == SnackbarResult.ActionPerformed) {
                                        taskList.add(taskToDelete)
                                    }
                                }
                                taskList.remove(taskToDelete)
                                selectedTask = null
                            },
                            dialogText = "Tem certeza que deseja deletar a tarefa $taskToDelete?",
                            dialogTitle = "Deletar Tarefa",
                        )
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun TaskScreenPreview() {
//    TaskVaultTheme {
//        TaskScreen()
//    }
//}