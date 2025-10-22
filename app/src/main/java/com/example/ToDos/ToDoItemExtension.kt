package com.example.ToDos

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
        if(importance != TodoItem.Importance.USUAL)
            put("importance", importance)

        if(color != Color.White)
            put("color", color.toArgb())

        if(deadline != null)
            put("deadline", deadline.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
    }

fun parse(json: JSONObject): TodoItem? {
    val uid = json.getString("uid")
    val importance = TodoItem.Importance.values()
        .find { it.name == json.optString("importance") }
        ?: TodoItem.Importance.USUAL
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
    return TodoItem(
        importance=importance,
        uid = uid,
        text = text,
        deadline = deadline,
        isDone = isDone,
        color = color)
}