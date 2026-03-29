package com.gblrod.taskvault.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gblrod.taskvault.components.AlertDialogDeleteTask
import com.gblrod.taskvault.components.BottomSheetEditTask
import com.gblrod.taskvault.components.BottomSheetNewTask
import com.gblrod.taskvault.components.TaskCard
import com.gblrod.taskvault.dto.TaskDto
import com.gblrod.taskvault.ui.theme.BackgroundColorOne
import com.gblrod.taskvault.ui.theme.BackgroundColorThree
import com.gblrod.taskvault.ui.theme.BackgroundColorTwo
import com.gblrod.taskvault.ui.theme.TaskVaultTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(modifier: Modifier = Modifier) {
    var newTask by remember { mutableStateOf("") }
    val taskList = remember { mutableStateListOf<TaskDto>() }
    val focus = LocalFocusManager.current
    var selectedTask by remember { mutableStateOf<TaskDto?>(null) }
    var selectedTaskEdit by remember { mutableStateOf<String?>(null) }
    var newTaskDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()

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
        },
        bottomBar = {
            // BottomBar()
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
                            TaskCard(
                                task = task,
                                onEditTask = {
                                    newTask = ""
                                    selectedTaskEdit = task.title
                                },
                                onDeleteTask = {
                                    selectedTask = task
                                }
                            )
                        }
                    }

                    if (newTaskDialog) {
                        BottomSheetNewTask(
                            task = newTask,
                            onTaskChange = { newTask = it },
                            onDismissRequest = {
                                newTaskDialog = false
                            },
                            onConfirmation = {
                                if (taskList.contains(TaskDto(title = newTask))) {
                                    Toast.makeText(
                                        context,
                                        "Tarefa $newTask já existente!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    scope.launch {
                                        val result = snackbarHostState.showSnackbar(
                                            message = "Tarefa $newTask adicionada",
                                            duration = SnackbarDuration.Short,
                                            actionLabel = "Desfazer"
                                        )
                                        if (result == SnackbarResult.ActionPerformed) {
                                            taskList.remove(TaskDto(title = newTask))
                                        }
                                    }
                                    taskList.add(TaskDto(title = newTask))
                                    newTaskDialog = false
                                }
                            },
                            sheetState = sheetState
                        )
                    }

                    selectedTaskEdit?.let { taskToEdit ->
                        BottomSheetEditTask(
                            task = newTask,
                            onTaskChange = { newTask = it },
                            onDismissRequest = {
                                selectedTaskEdit = null
                            },
                            onConfirmation = {
                                val isSameTask = newTask == taskToEdit
                                val alreadyTaskExists = taskList.contains(TaskDto(title = newTask))
                                if (!isSameTask && alreadyTaskExists) {
                                    Toast.makeText(
                                        context,
                                        "Tarefa $newTask já existente!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    val index = taskList.indexOf(TaskDto(title = taskToEdit))
                                    val oldTitleTask = taskList[index]
                                    taskList[index] = TaskDto(title = newTask)

                                    scope.launch {
                                        val result = snackbarHostState.showSnackbar(
                                            message = "Tarefa $taskToEdit editada",
                                            duration = SnackbarDuration.Short,
                                            actionLabel = "Desfazer"
                                        )

                                        if (result == SnackbarResult.ActionPerformed) {
                                            taskList[index] = oldTitleTask
                                        }
                                    }
                                    selectedTaskEdit = null
                                }
                            },
                            sheetState = sheetState,
                            dialogTitle = "Editando tarefa $taskToEdit",
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
                                        message = "Tarefa ${taskToDelete.title} deletada",
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
                            dialogText = "Tem certeza que deseja deletar a tarefa ${taskToDelete.title}?",
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