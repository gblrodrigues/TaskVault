package com.gblrod.taskvault.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gblrod.taskvault.ui.theme.ContainerButtonDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogDeleteTask(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String
) {
    AlertDialog(
        title = {
            Text(
                text = dialogTitle
            )
        },
        text = {
            Text(
                text = dialogText
            )
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
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Confirmar",
                    color = Color.Black
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(16.dp),
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
        shape = RoundedCornerShape(12.dp)
    )
}