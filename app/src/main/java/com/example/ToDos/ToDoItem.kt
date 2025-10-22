package com.example.ToDos

import androidx.compose.ui.graphics.Color
import java.time.LocalDateTime
import java.util.UUID



data class TodoItem(
    val text: String,
    val importance: Importance,
    val uid: String = UUID.randomUUID().toString(),
    val color: Color  = Color.White,
    val deadline: LocalDateTime? = null,
    val isDone: Boolean = false
) {
    enum class Importance {
        LOW,
        USUAL,
        HIGH
    }
}