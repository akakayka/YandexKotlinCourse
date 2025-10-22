package com.example.ToDos.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoTextField(
    text: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {



    TextField(
        value = text,
        onValueChange = onTextChanged,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text("Введите описание дела...")
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedIndicatorColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary
        ),
        singleLine = false,
        maxLines = 10
    )

}

@Preview(showBackground = true)
@Composable
fun TodoTextFieldPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            TodoTextField(
                text = "",
                onTextChanged = {}
            )
        }
    }
}