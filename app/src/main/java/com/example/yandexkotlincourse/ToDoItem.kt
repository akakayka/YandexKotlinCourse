package com.example.yandexkotlincourse

import androidx.compose.ui.graphics.Color
import java.time.LocalDateTime
import java.util.UUID

enum class Importance {
    LOW,
    USUAL,
    HIGH
}

data class TodoItem(val text: String, val importance: Importance,
                    val uid: String = UUID.randomUUID().toString(), val color: Color  = Color.White,
                    val deadline: LocalDateTime? = null, val isDone: Boolean = false) {
}