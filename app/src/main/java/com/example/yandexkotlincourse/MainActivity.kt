package com.example.kotlinhw_1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.yandexkotlincourse.FileStorage
import com.example.yandexkotlincourse.Importance
import com.example.yandexkotlincourse.TodoItem
import com.example.yandexkotlincourse.json

import com.example.yandexkotlincourse.ui.theme.YandexKotlinCourseTheme
import java.io.File
import java.time.LocalDateTime


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val filesDir = this.filesDir
        val storageFile = File(filesDir, "todo_items.json")
        val fileStorage = FileStorage(storageFile)

        val item1 = TodoItem(text = "Сделать дз kotlin", color = Color.Black,
            importance = Importance.HIGH, deadline = LocalDateTime.now().plusMinutes(1))
        val item2 = TodoItem(text = "Сходить на пару", importance = Importance.USUAL)
        val item3 = TodoItem(text = "Покушать", importance = Importance.LOW, color = Color.Blue,
            deadline = LocalDateTime.now().plusDays(5))

        Log.d("Test logs","Пример Item с deadline, color, importance в формате json: ${item1.json}")
        Log.d("Test logs","Пример Item без deadline, color, importance в формате json: ${item2.json}")

        Log.d("Test logs","Проверим, что изначально коллекция пустая: ${fileStorage.items}")

        fileStorage.add(item1)
        fileStorage.add(item2)
        fileStorage.add(item3)

        Log.d("Test logs","Коллекция после загрузки: ${fileStorage.items}")

        fileStorage.saveToFile()
        Thread.sleep(61000)

        Log.d("Test logs","Проверим удалилось ли дело после дедлайна: ${fileStorage.items}")

        fileStorage.remove(item3.uid)
        Log.d("Test logs","Проверка после удаления: ${fileStorage.items}")

        fileStorage.loadFromFile()
        Log.d("Test logs","Проверка после загрузки: ${fileStorage.items}")


        setContent {
            YandexKotlinCourseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YandexKotlinCourseTheme {
        Greeting("Android")
    }
}