package com.example.ToDos.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun ColorPickerDialog(
    currentColor: Color,
    onColorSelected: (Color) -> Unit,
    onDismiss: () -> Unit
) {
    var hue by remember { mutableStateOf(0f) }
    var saturation by remember { mutableStateOf(1f) }
    var brightness by remember { mutableStateOf(1f) }

    LaunchedEffect(currentColor) {
        val hsv = currentColor.toHsv()
        hue = hsv.hue
        saturation = hsv.saturation
        brightness = hsv.value
    }

    val currentColorValue = Color.hsv(hue, saturation, brightness)

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                onColorSelected(currentColorValue)
                onDismiss()
            }) {
                Text("Выбрать")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        },
        title = { Text("Выберите цвет") },
        text = {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(currentColorValue)
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(8.dp)
                            )
                    )

                    Slider(
                        value = brightness,
                        onValueChange = { newBrightness ->
                            brightness = newBrightness
                        },
                        modifier = Modifier.fillMaxWidth(),
                        valueRange = 0f..1f
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))



                BrightnessAwareColorPicker(
                    hue = hue,
                    saturation = saturation,
                    brightness = brightness,
                    onColorChanged = { newHue, newSaturation ->
                        hue = newHue
                        saturation = newSaturation
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    )
}

@Composable
fun BrightnessAwareColorPicker(
    hue: Float,
    saturation: Float,
    brightness: Float,
    onColorChanged: (Float, Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var containerSize by remember { mutableStateOf(Size.Zero) }
    var pickerPosition by remember { mutableStateOf(Offset.Zero) }

    LaunchedEffect(hue, saturation) {
        if (containerSize != Size.Zero) {
            pickerPosition = calculatePositionFromHsv(hue, saturation, containerSize.width, containerSize.height)
        }
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()

                        val newPosition = Offset(
                            x = (pickerPosition.x + dragAmount.x).coerceIn(0f, containerSize.width),
                            y = (pickerPosition.y + dragAmount.y).coerceIn(0f, containerSize.height)
                        )

                        pickerPosition = newPosition

                        val newHsv = calculateHsvFromPosition(
                            newPosition,
                            containerSize.width,
                            containerSize.height
                        )
                        onColorChanged(newHsv.hue, newHsv.saturation)
                    },
                    onDragStart = { offset ->
                        val newPosition = Offset(
                            x = offset.x.coerceIn(0f, containerSize.width),
                            y = offset.y.coerceIn(0f, containerSize.height)
                        )

                        pickerPosition = newPosition

                        val newHsv = calculateHsvFromPosition(
                            newPosition,
                            containerSize.width,
                            containerSize.height
                        )
                        onColorChanged(newHsv.hue, newHsv.saturation)
                    }
                )
            }
            .onGloballyPositioned { layoutCoordinates ->
                containerSize = Size(
                    width = layoutCoordinates.size.width.toFloat(),
                    height = layoutCoordinates.size.height.toFloat()
                )
                if (pickerPosition == Offset.Zero) {
                    pickerPosition = calculatePositionFromHsv(
                        hue,
                        saturation,
                        containerSize.width,
                        containerSize.height
                    )
                }
            }
    ) {

        Canvas(modifier = Modifier.matchParentSize()) {
            for (x in 0 until size.width.toInt() step 4) {
                for (y in 0 until size.height.toInt() step 4) {
                    val pos = Offset(x.toFloat(), y.toFloat())
                    val pixelHsv = calculateHsvFromPosition(pos, size.width, size.height)
                    val color = Color.hsv(pixelHsv.hue, pixelHsv.saturation, brightness)

                    drawCircle(
                        color = color,
                        radius = 2f,
                        center = pos
                    )
                }
            }
        }


        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = (pickerPosition.x - 12f).toInt(),
                        y = (pickerPosition.y - 12f).toInt()
                    )
                }
                .size(24.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(3.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.hsv(hue, saturation, brightness),
                        shape = RoundedCornerShape(9.dp)
                    )
            )
        }
    }
}


private fun calculatePositionFromHsv(hue: Float, saturation: Float, width: Float, height: Float): Offset {
    val normalizedX = hue / 360f
    val normalizedY = 1f - saturation

    return Offset(
        x = normalizedX * width,
        y = normalizedY * height
    )
}


private fun calculateHsvFromPosition(position: Offset, width: Float, height: Float): HsvColor {
    val normalizedX = position.x / width
    val normalizedY = position.y / height

    val hue = normalizedX * 360f
    val saturation = 1f - normalizedY

    return HsvColor(
        hue = hue.coerceIn(0f, 360f),
        saturation = saturation.coerceIn(0f, 1f),
        value = 1f
    )
}

data class HsvColor(
    val hue: Float,
    val saturation: Float,
    val value: Float
)

fun Color.toHsv(): HsvColor {
    val r = this.red
    val g = this.green
    val b = this.blue

    val max = maxOf(r, g, b)
    val min = minOf(r, g, b)
    val delta = max - min

    val hue = when {
        delta == 0f -> 0f
        max == r -> 60f * ((g - b) / delta % 6f)
        max == g -> 60f * ((b - r) / delta + 2f)
        else -> 60f * ((r - g) / delta + 4f)
    }

    val saturation = if (max == 0f) 0f else delta / max
    val value = max

    return HsvColor(
        hue = if (hue < 0) hue + 360f else hue,
        saturation = saturation,
        value = value
    )
}