package com.danielleitelima.resume.foundation.presentation.foundation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalNavHostController =
    staticCompositionLocalOf<NavHostController> { throw IllegalStateException("no NavHostController provided") }
