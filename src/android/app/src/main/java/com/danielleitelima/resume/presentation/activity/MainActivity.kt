package com.danielleitelima.resume.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.charite.memaef.android.ui.navigation.LocalNavHostController
import com.danielleitelima.resume.presentation.foundation.BaseRoute
import com.danielleitelima.resume.presentation.foundation.register
import com.danielleitelima.resume.presentation.foundation.theme.AppTheme
import com.danielleitelima.resume.presentation.route.home.HomeRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            MainScreenContent(
                navController = navController,
                startDestination = HomeRoute
            )
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun MainScreenContent(
        navController: NavHostController,
        startDestination: BaseRoute,
        destinationRoute: BaseRoute = startDestination,
    ) {
        LaunchedEffect(startDestination.route) {
            if (destinationRoute.route == navController.currentDestination?.route) return@LaunchedEffect
            navController.navigate(destinationRoute.route) {
                if (startDestination == HomeRoute) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
        }

        AppTheme {
            Surface {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CompositionLocalProvider(LocalNavHostController provides navController) {
                        CompositionLocalProvider(
                            LocalOverscrollConfiguration provides null
                        ) {
                            NavHost(
                                navController = navController,
                                startDestination = startDestination.route,
                            ) {
                                register(HomeRoute)
                            }
                        }
                    }
                }
            }
        }
    }
}