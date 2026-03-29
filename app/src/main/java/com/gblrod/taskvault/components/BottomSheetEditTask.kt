package com.gblrod.taskvault.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.gblrod.taskvault.R
import com.gblrod.taskvault.ui.theme.ContainerButtonDialog
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetEditTask(
    task: String,
    onTaskChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    sheetState: SheetState
) {
    val maxChar = 32
    val keyboard = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scroll = rememberScrollState()

    LaunchedEffect(task) {
        delay(100)
        focusRequester.requestFocus()
        keyboardController?.show()
    }
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = Color.DarkGray
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scroll)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = dialogTitle,
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = task,
                onValueChange = {
                    if (it.length <= maxChar) {
                        onTaskChange(it)
                    }
                },
                label = {
                    Text(
                        text = "Altere o título"
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
                    focusedContainerColor = Color.LightGray,
                    unfocusedContainerColor = Color.LightGray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Gray,
                    focusedLabelColor = Color.LightGray,
                    unfocusedLabelColor = Color.Black,
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
                    if (task.isNotBlank()) {
                        IconButton(
                            onClick = {
                                onTaskChange("")
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Ícone de limpar campo",
                                tint = Color.Black
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
            ) {
                OutlinedButton(
                    onClick = { onDismissRequest() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(
                        width = 2.dp,
                        color = ContainerButtonDialog
                    ),
                ) {
                    Text(
                        text = "Cancelar",
                        color = ContainerButtonDialog
                    )
                }

                OutlinedButton(
                    onClick = { onConfirmation() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ContainerButtonDialog
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = task.isNotBlank()
                ) {
                    Text(
                        text = "Salvar",
                        color = Color.White
                    )
                }
            }
        }
    }

//    AlertDialog(
//        title = {
//            Text(
//                text = dialogTitle
//            )
//        },
//        text = {
//            OutlinedTextField(
//                value = task,
//                onValueChange = {
//                    if (it.length <= maxChar) {
//                        onTaskChange(it)
//                    }
//                },
//                label = {
//                    Text(
//                        text = "Altere o título"
//                    )
//                },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Search
//                ),
//                keyboardActions = KeyboardActions(
//                    onSearch = { keyboard?.hide() }
//                ),
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color.LightGray,
//                    unfocusedContainerColor = Color.LightGray,
//                    focusedTextColor = Color.Black,
//                    unfocusedTextColor = Color.Black,
//                    focusedIndicatorColor = Color.Gray,
//                    unfocusedIndicatorColor = Color.Gray,
//                    focusedLabelColor = Color.LightGray,
//                    unfocusedLabelColor = Color.Black,
//                    cursorColor = Color.Black,
//                ),
//                leadingIcon = {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_article),
//                        contentDescription = "Ícone de Tarefa",
//                        tint = Color.Black
//                    )
//                },
//                trailingIcon = {
//                    if (task.isNotBlank()) {
//                        IconButton(
//                            onClick = {
//                                onTaskChange("")
//                            }
//                        ) {
//                            Icon(
//                                imageVector = Icons.Default.Clear,
//                                contentDescription = "Ícone de limpar campo",
//                                tint = Color.Black
//                            )
//                        }
//                    }
//                },
//                shape = RoundedCornerShape(12.dp),
//                modifier = Modifier.fillMaxWidth().focusRequester(focusRequester)
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//        },
//        onDismissRequest = {
//            onDismissRequest()
//        },
//        confirmButton = {
//            TextButton(
//                onClick = { onConfirmation() },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = ContainerButtonDialog
//                ),
//                shape = RoundedCornerShape(12.dp),
//                enabled = task.isNotBlank()
//            ) {
//                Text(
//                    text = "Salvar",
//                    color = Color.White
//                )
//            }
//        },
//        dismissButton = {
//            TextButton(
//                onClick = { onDismissRequest() },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.Transparent
//                ),
//                shape = RoundedCornerShape(12.dp),
//                border = BorderStroke(
//                    width = 2.dp,
//                    color = ContainerButtonDialog
//                ),
//            ) {
//                Text(
//                    text = "Cancelar",
//                    color = ContainerButtonDialog
//                )
//            }
//        },
//        shape = RoundedCornerShape(12.dp),
//        modifier = Modifier.border(
//            width = 2.dp,
//            color = Color.Gray,
//            shape = RoundedCornerShape(12.dp)
//        )
//    )
}