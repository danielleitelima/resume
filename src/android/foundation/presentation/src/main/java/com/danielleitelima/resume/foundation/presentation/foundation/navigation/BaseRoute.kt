package com.danielleitelima.resume.foundation.presentation.foundation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Left
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Right
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

typealias EnterTransitionScope = AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition
typealias ExitTransitionScope = AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition

const val TRANSITION_DURATION = 400

abstract class BaseRoute {
    open val route: String
        get() = this.javaClass.name

    open val arguments: List<NamedNavArgument>
        get() = emptyList()

    open val deepLinks: List<NavDeepLink>
        get() = emptyList()

    open val defaultEnterTransition: EnterTransitionScope? = {
        slideIntoContainer(
            towards = Left,
            animationSpec = tween(TRANSITION_DURATION)
        )
    }

    open val defaultExitTransition: ExitTransitionScope? = {
        slideOutOfContainer(
            towards = Left,
            animationSpec = tween(TRANSITION_DURATION)
        )
    }

    open val defaultPopEnterTransition: EnterTransitionScope? = {
        slideIntoContainer(
            towards = Right,
            animationSpec = tween(TRANSITION_DURATION)
        )
    }

    open val defaultPopExitTransition: ExitTransitionScope? = {
        slideOutOfContainer(
            towards = Right,
            animationSpec = tween(TRANSITION_DURATION)
        )
    }

    open fun registerTo(builder: NavGraphBuilder) {
        builder.composable(
            route = route,
            arguments = arguments,
            deepLinks = deepLinks,
            enterTransition = defaultEnterTransition,
            exitTransition = defaultExitTransition,
            popEnterTransition = defaultPopEnterTransition,
            popExitTransition = defaultPopExitTransition
        ) {
            Content(it)
        }
    }

    open fun registerTo(builder: NavGraphBuilder, navController: NavHostController) {
        builder.composable(
            route = route,
            arguments = arguments,
            deepLinks = deepLinks,
            enterTransition = defaultEnterTransition,
            exitTransition = defaultExitTransition,
            popEnterTransition = defaultPopEnterTransition,
            popExitTransition = defaultPopExitTransition
        ) {
            Content(it, navController)
        }
    }

    @Composable
    open fun Content(backStackEntry: NavBackStackEntry) {
    }

    @Composable
    open fun Content(backStackEntry: NavBackStackEntry, navController: NavHostController) {
    }

}

fun NavGraphBuilder.register(route: BaseRoute) {
    route.registerTo(this)
}

fun NavGraphBuilder.register(
    navController: NavHostController,
    route: BaseRoute
) {
    route.registerTo(this, navController)
}