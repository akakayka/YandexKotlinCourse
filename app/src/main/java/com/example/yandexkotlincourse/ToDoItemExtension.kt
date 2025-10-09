package com.example.yandexkotlincourse

import org.json.JSONObject
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val TodoItem.json: JSONObject
    get() = JSONObject().apply {
        put("uid", uid)
        put("text", text)
        put("isDone", isDone)
        if(importance != Importance.USUAL)
            put("importance", importance)

        if(color != Color.White)
            put("color", color.toArgb())

        if(deadline != null)
            put("deadline", deadline.toString())
    }

fun parse(json: JSONObject): TodoItem? {
    try {
        val uid = json.getString("uid")
        val importance = when (json.optString("importance")) {
            "LOW" -> Importance.LOW
            "HIGH" -> Importance.HIGH
            else -> Importance.USUAL
        }
        val isDone = json.getBoolean("isDone")
        val text = json.getString("text")
        val color = if (json.has("color")) {
            Color(json.getInt("color"))
        } else {
            Color.White
        }

        val deadline = if (json.has("deadline")) {
            LocalDateTime.parse(json.getString("deadline"), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        } else {
            null
        }
        return TodoItem(importance=importance, uid = uid, text = text,
                        deadline = deadline, isDone = isDone, color = color)
    }
    catch (e: Exception){
        return null
    }
}