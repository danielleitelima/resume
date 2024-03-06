package com.danielleitelima.resume.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Grayscale11,
    onPrimary = Grayscale1,
    secondary = Grayscale12.copy(alpha = 0.25f),
    onSecondary = Grayscale1,
    tertiary = Grayscale2,
    surface = Grayscale12,
    onSurface = Grayscale1,
)

private val LightColorScheme = lightColorScheme(
    primary = Grayscale2,
    onPrimary = Grayscale12,
    secondary = Grayscale1,
    onSecondary = Grayscale12,
    tertiary = Grayscale2,
    surface = Grayscale11,
    onSurface = Grayscale1,
)

@Composable
fun ResumeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme.not()
        }
    }

    CompositionLocalProvider(LocalTypography provides TypographyFamily()) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }

}