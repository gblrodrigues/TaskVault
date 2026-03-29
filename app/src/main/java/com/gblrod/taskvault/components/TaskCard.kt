package com.gblrod.taskvault.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gblrod.taskvault.dto.TaskDto
import com.gblrod.taskvault.ui.theme.TaskCompletedColor

@Composable
fun TaskCard(
    task: TaskDto,
    onEditTask: () -> Unit,
    onDeleteTask: () -> Unit
) {
    var checkedState by remember { mutableStateOf(task.isCompleted) }
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = {
                    checkedState = !checkedState
                    task.isCompleted = checkedState
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = TaskCompletedColor,
                    uncheckedColor = Color.Black
                )
            )

            Text(
                text = task.title,
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textDecoration = if (checkedState) TextDecoration.LineThrough else null,
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    )
            )

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    onEditTask()
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Ícone de editar task",
                        tint = Color.Black
                    )
                }

                IconButton(onClick = {
                    onDeleteTask()
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Ícone de deletar task",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}