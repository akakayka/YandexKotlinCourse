package com.example.ToDos.ui.screens



import ItemTextField
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ToDos.TodoItem
import com.example.ToDos.ui.components.ColorSelection
import com.example.ToDos.ui.components.ImportancePicker
import com.example.ToDos.ui.components.SimpleButton
import com.example.ToDos.ui.components.colorPalette
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private fun formatDate(timestamp: Long): String {
    val date = Date(timestamp)
    val formatter = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    return formatter.format(date)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEditScreen() {
    var todoText by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    val datePickerState = rememberDatePickerState()
    var isCompleted by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf(colorPalette[0].color) }
    var selectedPriority by remember { mutableStateOf(TodoItem.Importance.USUAL) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ItemTextField(
            text = todoText,
            onTextChanged = { newText ->
                todoText = newText
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        SimpleButton(
            text = "Выбрать дату",
            onClick = { showDatePicker = true },
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (selectedDate != null) {
                "Дедлайн: ${formatDate(selectedDate!!)}"
            } else {
                "Дедлайн не установлен"
            },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isCompleted,
                onCheckedChange = { checked ->
                    isCompleted = checked
                    if(checked)
                        selectedColor = Color.Gray
                },
                enabled = true
            )

            Text(
                text = "Дело сделано",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        ColorSelection(
            selectedColor = selectedColor,
            onColorSelected = { color -> selectedColor = color }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ImportancePicker(
            selectedPriority = selectedPriority,
            onPrioritySelected = { priority -> selectedPriority = priority }
        )

            if (showDatePicker) {
                DatePickerDialog(onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = {
                            selectedDate = datePickerState.selectedDateMillis
                            showDatePicker = false
                        }) {
                            Text("OK")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }


        }
    }
