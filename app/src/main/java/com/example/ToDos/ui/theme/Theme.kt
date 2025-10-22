package com.example.ToDos.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryGreen,
    secondary = SecondaryGreen,
    tertiary = TertiaryGreen
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6B8E6B),      // Пастельный зеленый
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD4E8D4), // Очень светлый зеленый
    onPrimaryContainer = Color(0xFF2A4A2A),

    secondary = Color(0xFF8BA68B),    // Дополнительный зеленый
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFE8F4E8),
    onSecondaryContainer = Color(0xFF3A563A),

    tertiary = Color(0xFF7D9A7D),     // Третичный зеленый
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFE0EDE0),
    onTertiaryContainer = Color(0xFF354F35),

    background = Color(0xFFF8FBF8),   // Светлый зеленоватый фон
    onBackground = Color(0xFF1A1C1A),
    surface = Color(0xFFFEFEFE),      // Чистый белый для поверхностей
    onSurface = Color(0xFF1A1C1A),

    surfaceVariant = Color(0xFFE8EFE8), // Вариант поверхности
    onSurfaceVariant = Color(0xFF444F44),

    outline = Color(0xFFB8C9B8),      // Цвет контуров
    outlineVariant = Color(0xFFD8E3D8),
)



@Composable
fun ToDosTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}