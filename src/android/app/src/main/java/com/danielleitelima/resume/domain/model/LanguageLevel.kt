package com.danielleitelima.resume.domain.model

import androidx.compose.ui.graphics.Color

enum class LanguageLevel(val value: Int, val color: Color) {
    BASIC(1, Color(0xFFFDBB30)),
    INTERMEDIATE(2, Color(0xFFFDBB30)),
    ADVANCED(3, Color(0xFF32B566)),
    FLUENT(4, Color(0xFF0296D8)),
    ;

    companion object {
        fun fromValue(value: Int): LanguageLevel {
            return when (value) {
                1 -> BASIC
                2 -> INTERMEDIATE
                3 -> ADVANCED
                4 -> FLUENT
                else -> throw IllegalArgumentException("Invalid value $value")
            }
        }
    }

}