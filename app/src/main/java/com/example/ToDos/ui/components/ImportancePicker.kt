package com.example.ToDos.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ToDos.TodoItem


@Composable
fun ImportancePicker(
    selectedPriority: TodoItem.Importance,
    onPrioritySelected: (TodoItem.Importance) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Важность дела",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TodoItem.Importance.values().forEach { priority ->
                    ImportanceChip(
                        priority = priority,
                        isSelected = priority == selectedPriority,
                        onPrioritySelected = onPrioritySelected
                    )
                }
            }
        }
    }
}

@Composable
fun ImportanceChip(
    priority: TodoItem.Importance,
    isSelected: Boolean,
    onPrioritySelected: (TodoItem.Importance) -> Unit,
    modifier: Modifier = Modifier
) {
    val (text, color) = when (priority) {
        TodoItem.Importance.LOW -> "Низкая" to MaterialTheme.colorScheme.primary
        TodoItem.Importance.USUAL -> "Обычная" to MaterialTheme.colorScheme.primary
        TodoItem.Importance.HIGH -> "Высокая" to MaterialTheme.colorScheme.primary
    }

    androidx.compose.material3.FilterChip(
        selected = isSelected,
        onClick = { onPrioritySelected(priority) },
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        modifier = modifier,
        colors = androidx.compose.material3.FilterChipDefaults.filterChipColors(
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            selectedContainerColor = color,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    )
}