package com.danielleitelima.resume.foundation.presentation.foundation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavHostController

interface Route{
    val uri: String
    val arguments: List<NamedNavArgument>
        get() = emptyList()
    val deepLinks: List<NavDeepLink>
        get() = emptyList()
}

fun NavHostController.navigate(route: Route) {
    navigate(route.uri)
}