package com.example.ToDos

import org.json.JSONArray
import java.io.File
import java.time.LocalDateTime
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class FileStorage(private val storageFile: File) {

    private val logger: Logger = LoggerFactory.getLogger(FileStorage::class.java)
    private val _items: MutableList<TodoItem> = mutableListOf()
    val items: List<TodoItem>
        get() {
            removeExpiredItems()
            logger.debug("Получение списка дел: ${_items}")
            return _items.toList()
        }

    fun add(item: TodoItem) {
        logger.info("Добавление задачи: ${item.text}, uid задачи: ${item.uid}")
        _items.add(item)
        logger.debug("Задача добавлена. Всего задач: ${_items.size}")
    }

    fun remove(uid: String): Boolean {
        logger.info("Удаление задачи с uid - $uid")
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
        logger.info("Задачи сохранены в файл - ${storageFile.name}")
    }

    fun loadFromFile() {
        if (!storageFile.exists()) {
            logger.warn("Невозможно загрузить задачи из файла ${storageFile.name}\nТакого файла не существует")
            return
        }
        val jsonString = storageFile.readText()
        if (jsonString.isBlank()) {
            logger.warn("Файл ${storageFile.name} - пуст. Загружено 0 задач")
            return
        }
        val jsonArray = JSONArray(jsonString)
        _items.clear()
        for (i in 0 until jsonArray.length()) {
            if (!jsonArray.isNull(i)) {
                val jsonItem = jsonArray.getJSONObject(i)
                val newItem = parse(jsonItem)
                if (newItem != null)
                    _items.add(newItem)
            }
        }
        removeExpiredItems()
        logger.info("Из файла ${storageFile.name} загружено ${_items.size} задач")
    }
}