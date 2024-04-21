package com.danielleitelima.resume.foundation.presentation.foundation.theme

import androidx.compose.ui.unit.Dp

sealed class Dimension(
    val value: Int,
) {
    data object Icon : Dimension(24)
    data object CornerRadius : Dimension(12)
    data object Elevation : Dimension(4)

    sealed class Spacing(
        value: Int,
    ) : Dimension(value) {
        data object XXS : Spacing(4)
        data object XS : Spacing(8)
        data object S : Spacing(12)
        data object M : Spacing(16)
        data object L : Spacing(24)
        data object XL : Spacing(32)
        data object XXL : Spacing(48)
    }

    inline val dp: Dp get() = Dp(value = value.toFloat())
}