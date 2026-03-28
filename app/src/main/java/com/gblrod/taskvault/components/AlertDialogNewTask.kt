package com.gblrod.taskvault.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.gblrod.taskvault.R
import com.gblrod.taskvault.ui.theme.ContainerButtonDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogNewTask(
    task: String,
    onTaskChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String
) {
    val maxChar = 32
    val keyboard = LocalSoftwareKeyboardController.current

    AlertDialog(
        title = {
            Text(
                text = dialogTitle
            )
        },
        text = {
            OutlinedTextField(
                value = task,
                onValueChange = {
                    if (it.length <= maxChar) {
                        onTaskChange(it)
                    }
                },
                label = {
                    Text(
                        text = "Digite uma tarefa"
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
                                contentDescription = "Ícone de Tarefa",
                                tint = Color.Black
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirmation() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ContainerButtonDialog
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = task.isNotBlank()
            ) {
                Text(
                    text = "Adicionar",
                    color = Color.White
                )
            }
        },
        dismissButton = {
            TextButton(
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
        },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.border(
            width = 2.dp,
            color = Color.Gray,
            shape = RoundedCornerShape(12.dp)
        )
    )
}