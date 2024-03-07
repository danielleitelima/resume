package com.charite.memaef.android.ui.navigation

import android.os.Parcelable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.danielleitelima.resume.presentation.foundation.BaseRoute

internal val LocalNavHostController =
    staticCompositionLocalOf<NavHostController> { throw IllegalStateException("no NavHostController provided") }

fun NavHostController.navigate(route: BaseRoute, builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route.route) {
        builder()
    }
}

fun NavHostController.navigate(route: String, vararg args: Pair<String, Parcelable>) {
    navigate(route)

    requireNotNull(currentBackStackEntry?.arguments).apply {
        args.forEach { (key: String, arg: Parcelable) ->
            putParcelable(key, arg)
        }
    }
}

fun NavHostController.navigateStringArguments(route: String, vararg args: Pair<String, String>) {
    navigate(route)

    requireNotNull(currentBackStackEntry?.arguments).apply {
        args.forEach { (key: String, arg: String) ->
            putString(key, arg)
        }
    }
}

inline fun <reified T : Parcelable> NavBackStackEntry.requiredArg(key: String): T {
    return requireNotNull(arguments) { "arguments bundle is null" }.run {
        requireNotNull(getParcelable(key)) { "argument for $key is null" }
    }
}
