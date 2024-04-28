package com.danielleitelima.resume.foundation.presentation.foundation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

typealias EnterTransitionScope = AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition
typealias ExitTransitionScope = AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition

interface Screen {
    val route: Route

    val transitionBehaviour: TransitionBehaviour
        get() = DefaultTransition

    @Composable
    fun Content(backStackEntry: NavBackStackEntry)
}

interface TransitionBehaviour {
    val enterTransition: EnterTransitionScope?
    val exitTransition: ExitTransitionScope?
    val popEnterTransition: EnterTransitionScope?
    val popExitTransition: ExitTransitionScope?
}

object DefaultTransition: TransitionBehaviour {
    private const val TRANSITION_DURATION = 400

    override val enterTransition: EnterTransitionScope
        get() = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(TRANSITION_DURATION)
            )
        }
    override val exitTransition: ExitTransitionScope
        get() = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(TRANSITION_DURATION)
            )
        }
    override val popEnterTransition: EnterTransitionScope
        get() = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(TRANSITION_DURATION)
            )
        }

    override val popExitTransition: ExitTransitionScope
        get() = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(TRANSITION_DURATION)
            )
        }
}

fun NavGraphBuilder.register(screen: Screen) {

    composable(
        route = screen.route.uri,
        arguments = screen.route.arguments,
        deepLinks = screen.route.deepLinks,
        enterTransition = screen.transitionBehaviour.enterTransition,
        exitTransition = screen.transitionBehaviour.exitTransition,
        popEnterTransition = screen.transitionBehaviour.popEnterTransition,
        popExitTransition = screen.transitionBehaviour.popExitTransition
    ) {
        screen.Content(it)
    }
}