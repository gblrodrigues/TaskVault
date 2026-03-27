package com.gblrod.taskvault.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.gblrod.taskvault.R
import com.gblrod.taskvault.components.AlertDialogDeleteTask
import com.gblrod.taskvault.ui.theme.BackgroundColorOne
import com.gblrod.taskvault.ui.theme.BackgroundColorThree
import com.gblrod.taskvault.ui.theme.BackgroundColorTwo
import kotlinx.coroutines.launch

@Composable
fun TaskScreen(modifier: Modifier = Modifier) {
    var newTask by remember { mutableStateOf("") }
    val taskList = remember { mutableStateListOf<String>() }
    val maxChar = 32
    val focus = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    var selectedTask by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

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
                .padding(
                    horizontal = 16.dp,
                    vertical = 36.dp
                )
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focus.clearFocus()
                    })
                }
        )
        {
            Text(
                text = "Minhas Tarefas (${taskList.size})",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = newTask,
                    onValueChange = {
                        if (it.length <= maxChar) {
                            newTask = it
                        }
                    },
                    label = {
                        Text(
                            text = "Digite uma tarefa",
                            color = Color.Black
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = { keyboard?.hide() }
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedTextColor = Color.White,
                        unfocusedIndicatorColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        cursorColor = Color.Black,
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_article),
                            contentDescription = "Ícone de Tarefa",
                            tint = Color.Black
                        )
                    },

                    trailingIcon = {
                        if (newTask.isNotBlank())
                            IconButton(
                                onClick = {
                                    newTask = ""
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Ícone de Limpar Campo",
                                    tint = Color.Black
                                )
                            }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            if (taskList.contains(newTask)) {
                                Toast.makeText(
                                    context,
                                    "Já existe uma tarefa com este nome!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                taskList.add(newTask)
                                newTask = ""
                                focus.clearFocus()
                                keyboard?.hide()
                            }
                        },
                        enabled = newTask.isNotBlank(),
                        shape = RoundedCornerShape(12.dp),
                        border = if (newTask.isBlank()) BorderStroke(
                            width = 1.dp,
                            color = Color.Black
                        ) else null
                    ) {
                        Text(
                            text = "Adicionar"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
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
                                        contentDescription = "Ícone de deletar task"
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
                selectedTask?.let { taskToDelete ->
                    AlertDialogDeleteTask(
                        onDismissRequest = {
                            selectedTask = null
                            // Log.i("AlertDialogCancel", "CLICOU EM CANCELAR")
                        },
                        onConfirmation = {
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "$taskToDelete deletada!",
                                    duration = SnackbarDuration.Short,
                                    actionLabel = "Desfazer"
                                )

                                if (result == SnackbarResult.ActionPerformed) {
                                    taskList.add(taskToDelete)
                                }
                            }
                            // Log.i("AlertDialogConfirm", "CLICOU EM CONFIRM")
                            taskList.remove(taskToDelete)
                            selectedTask = null
                        },
                        dialogText = "Tem certeza que deseja deletar a tarefa $taskToDelete?",
                        dialogTitle = "Deletar Tarefa",
                    )
                }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp),
        ) { data ->
            Snackbar(
                snackbarData = data,
                shape = RoundedCornerShape(12.dp)
            )
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