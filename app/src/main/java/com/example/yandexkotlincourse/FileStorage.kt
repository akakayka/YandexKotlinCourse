package com.example.yandexkotlincourse

import org.json.JSONArray
import java.io.File
import java.time.LocalDateTime

class FileStorage(private val storageFile: File) {

    private val _items: MutableList<TodoItem> = mutableListOf()
    val items: List<TodoItem>
        get() {
            removeExpiredItems()
            return _items.toList()
        }

    fun add(item: TodoItem) {
        _items.add(item)
    }

    fun remove(uid: String): Boolean {
        return _items.removeIf { it.uid == uid }
    }

    private fun removeExpiredItems(){
        val now = LocalDateTime.now()
        val expiredItems = _items.filter {i -> i.deadline != null && now.isAfter(i.deadline)}
        for(i in expiredItems)
            remove(i.uid)
    }



    fun saveToFile() {
        removeExpiredItems()
        val jsonItems = JSONArray()
        items.forEach { item -> jsonItems.put(item.json)}
        storageFile.writeText(jsonItems.toString())
    }

    fun loadFromFile() {
        if (!storageFile.exists()) {
            return
        }
        val jsonString = storageFile.readText()
        val jsonArray = JSONArray(jsonString)
        _items.clear()
        for (i in 0 until jsonArray.length()) {
            val jsonItem = jsonArray.getJSONObject(i)
            val newItem = parse(jsonItem)
            if (newItem != null)
                _items.add(newItem)
        }
        removeExpiredItems()
    }
}