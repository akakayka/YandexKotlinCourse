package com.example.ToDos


import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

import com.example.ToDos.ui.theme.ToDosTheme
import java.io.File


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val filesDir = this.filesDir
        val storageFile = File(filesDir, "todo_items.json")
        val fileStorage = FileStorage(storageFile)

        fileStorage.loadFromFile()

        setContent {
            ToDosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {


                        Button(
                            onClick = {
                                val items = fileStorage.items

                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Список дел")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                val newItem = TodoItem(
                                    text = "Новая задача",
                                    importance = TodoItem.Importance.USUAL
                                )
                                fileStorage.add(newItem)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Добавить дело")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                val items = fileStorage.items
                                if (items.isNotEmpty()) {
                                    fileStorage.remove(items.first().uid)
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Удалить первое дело")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                fileStorage.saveToFile()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Сохранить в файл")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                fileStorage.loadFromFile()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Загрузить из файла")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                for(i in 1..100000) {
                                    val leakyObject = LeakTestClass()
                                    leakyObject.createLeak()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFD32F2F),
                                contentColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Утечка памяти")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                repeat(10) { i ->
                                    Thread {
                                        var sum = 0L
                                        for (y in 0..5_000_000) {
                                            sum += y * i
                                            if (y % 100000 == 0) {
                                                Thread.yield()
                                            }
                                        }
                                    }.start()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFD32F2F),
                                contentColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Многопоточная нагрузка")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                val startTime = System.currentTimeMillis()

                                fun fibonacci(n: Int): Long {
                                    return if (n <= 1) n.toLong()
                                    else fibonacci(n - 1) + fibonacci(n - 2)
                                }

                                val fibNum1 = fibonacci(39)
                                val fibNum2 = fibonacci(36)
                                val endTime = System.currentTimeMillis()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFD32F2F),
                                contentColor = Color.White
                            ),

                        ) {
                            Text("Вычисление чисел фибаначи")
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
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
    ToDosTheme {
        Greeting("Android")
    }
}