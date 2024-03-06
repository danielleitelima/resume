package com.danielleitelima.resume.domain.model

enum class LanguageLevel(val value: Int) {
    BASIC(1),
    INTERMEDIATE(2),
    ADVANCED(3),
    FLUENT(4),
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