package com.example.ToDos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Модель для цветов
data class TodoColor(
    val color: Color,
    val name: String
)

// Палитра цветов
val colorPalette = listOf(
    TodoColor(Color(0xFF6B8E6B), "Зеленый"),
    TodoColor(Color(0xFF8B4513), "Коричневый"),
    TodoColor(Color(0xFF4682B4), "Синий"),
    TodoColor(Color(0xFFD2691E), "Оранжевый"),
    TodoColor(Color(0xFF9370DB), "Фиолетовый"),
    TodoColor(Color(0xFF20B2AA), "Бирюзовый")
)

@Composable
fun ColorSelection(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
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
                text = "Цвет дела",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                colorPalette.forEach { todoColor ->
                    ColorOption(
                        color = todoColor.color,
                        isSelected = todoColor.color == selectedColor,
                        onColorSelected = { onColorSelected(todoColor.color) }
                    )
                }
            }
        }
    }
}

@Composable
fun ColorOption(
    color: Color,
    isSelected: Boolean,
    onColorSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color)
            .border(
                width = 2.dp,
                color = if (isSelected) Color.Black else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onColorSelected() },
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Выбрано",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColorSelectionPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ColorSelection(
                selectedColor = colorPalette[0].color,
                onColorSelected = {}
            )
        }
    }
}