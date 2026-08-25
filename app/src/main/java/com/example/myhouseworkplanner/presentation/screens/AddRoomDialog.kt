package com.example.myhouseworkplanner.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddRoomDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, icon: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf("🛁") }

    val defaultIcons = listOf("🛁", "🍳", "🛏️", "🛋️", "🚪", "🌿")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Новая комната") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Название комнаты") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Text("Выберите иконку:", style = MaterialTheme.typography.bodySmall)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    defaultIcons.forEach { emoji ->
                        FilterChip(
                            selected = selectedIcon == emoji,
                            onClick = { selectedIcon = emoji },
                            label = { Text(emoji) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onConfirm(name.trim(), selectedIcon)
                    }
                },
                enabled = name.isNotBlank()
            ) {
                Text("Создать")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}