package com.example.myhouseworkplanner.presentation.screens.roomScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.myhouseworkplanner.domain.model.IntervalUnit

@Composable
fun AddItemDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, icon: String, intervalValue: Int, intervalUnit: IntervalUnit) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf("🧽") }
    var intervalValueText by remember { mutableStateOf("7") }
    var selectedUnit by remember { mutableStateOf(IntervalUnit.DAYS) }

    val defaultIcons = listOf("🧽", "🚽", "🚿", "🪞", "🧺", "✨", "🍳", "🧹")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Новый объект уборки") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Название (напр. Зеркало)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Иконка:", style = MaterialTheme.typography.bodySmall)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    defaultIcons.take(4).forEach { emoji ->
                        FilterChip(
                            selected = selectedIcon == emoji,
                            onClick = { selectedIcon = emoji },
                            label = { Text(emoji) }
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    defaultIcons.drop(4).forEach { emoji ->
                        FilterChip(
                            selected = selectedIcon == emoji,
                            onClick = { selectedIcon = emoji },
                            label = { Text(emoji) }
                        )
                    }
                }

                OutlinedTextField(
                    value = intervalValueText,
                    onValueChange = { if (it.all { char -> char.isDigit() }) intervalValueText = it },
                    label = { Text("Интервал") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Единица измерения:", style = MaterialTheme.typography.bodySmall)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IntervalUnit.entries.forEach { unit ->
                        val unitTitle = when (unit) {
                            IntervalUnit.HOURS -> "Час"
                            IntervalUnit.DAYS -> "Дни"
                            IntervalUnit.WEEKS -> "Нед"
                            IntervalUnit.MONTHS -> "Мес"
                        }
                        FilterChip(
                            selected = selectedUnit == unit,
                            onClick = { selectedUnit = unit },
                            label = { Text(unitTitle) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val value = intervalValueText.toIntOrNull() ?: 1
                    if (name.isNotBlank()) {
                        onConfirm(name.trim(), selectedIcon, value, selectedUnit)
                    }
                },
                enabled = name.isNotBlank() && intervalValueText.isNotBlank()
            ) {
                Text("Добавить")
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