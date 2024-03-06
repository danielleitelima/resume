package com.danielleitelima.resume.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.danielleitelima.resume.R

const val text2xsSize = 10
const val textXsSize = 12
const val textSSize = 14
const val textMSize = 16
const val textLSize = 18
const val textXlSize = 20
const val displayXsSize = 24
const val displaySSize = 30
const val displayMSize = 36
const val displayLSize = 48
const val displayXlSize = 60
const val display2xlSize = 72

val LocalTypography = compositionLocalOf { TypographyFamily() }

val MaterialTheme.typographyFamily: TypographyFamily
    @Composable
    get() = LocalTypography.current

val lexandFontFamily = FontFamily(
    Font(R.font.lexend_thin, FontWeight.Thin),
    Font(R.font.lexend_extr_light, FontWeight.ExtraLight),
    Font(R.font.lexend_light, FontWeight.Light),
    Font(R.font.lexend_regular, FontWeight.Normal),
    Font(R.font.lexend_medium, FontWeight.Medium),
    Font(R.font.lexend_semi_bold, FontWeight.SemiBold),
    Font(R.font.lexend_bold, FontWeight.Bold),
    Font(R.font.lexend_extra_bold, FontWeight.ExtraBold),
    Font(R.font.lexend_black, FontWeight.Black),
)

data class TypographyUnit(
    val thin: TextStyle,
    val extraLight: TextStyle,
    val light: TextStyle,
    val regular: TextStyle,
    val medium: TextStyle,
    val semiBold: TextStyle,
    val bold: TextStyle,
    val extraBold: TextStyle,
    val black: TextStyle,
)

data class TypographyFamily(
    val text2xs: TypographyUnit = generateTypographyUnit(text2xsSize.sp),
    val textXs: TypographyUnit = generateTypographyUnit(textXsSize.sp),
    val textS: TypographyUnit = generateTypographyUnit(textSSize.sp),
    val textM: TypographyUnit = generateTypographyUnit(textMSize.sp),
    val textL: TypographyUnit = generateTypographyUnit(textLSize.sp),
    val textXl: TypographyUnit = generateTypographyUnit(textXlSize.sp),
    val displayXs: TypographyUnit = generateTypographyUnit(displayXsSize.sp),
    val displayS: TypographyUnit = generateTypographyUnit(displaySSize.sp),
    val displayM: TypographyUnit = generateTypographyUnit(displayMSize.sp),
    val displayL: TypographyUnit = generateTypographyUnit(displayLSize.sp),
    val displayXl: TypographyUnit = generateTypographyUnit(displayXlSize.sp),
    val display2xl: TypographyUnit = generateTypographyUnit(display2xlSize.sp),
)


fun generateTypographyUnit(size: TextUnit) = TypographyUnit(
    thin = TextStyle(
        fontFamily = lexandFontFamily,
        fontSize = size,
        fontWeight = FontWeight.Thin,
    ),
    extraLight = TextStyle(
        fontFamily = lexandFontFamily,
        fontSize = size,
        fontWeight = FontWeight.ExtraLight,
    ),
    light = TextStyle(
        fontFamily = lexandFontFamily,
        fontSize = size,
        fontWeight = FontWeight.Light,
    ),
    regular = TextStyle(
        fontFamily = lexandFontFamily,
        fontSize = size,
        fontWeight = FontWeight.Normal,
    ),
    medium = TextStyle(
        fontFamily = lexandFontFamily,
        fontSize = size,
        fontWeight = FontWeight.Medium,
    ),
    semiBold = TextStyle(
        fontFamily = lexandFontFamily,
        fontSize = size,
        fontWeight = FontWeight.SemiBold,
    ),
    bold = TextStyle(
        fontFamily = lexandFontFamily,
        fontSize = size,
        fontWeight = FontWeight.Bold,
    ),
    extraBold = TextStyle(
        fontFamily = lexandFontFamily,
        fontSize = size,
        fontWeight = FontWeight.ExtraBold,
    ),
    black = TextStyle(
        fontFamily = lexandFontFamily,
        fontSize = size,
        fontWeight = FontWeight.Black,
    ),
)
