package com.danielleitelima.resume.activity

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
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.danielleitelima.resume.chat.presentation.foundation.registerChatRoutes
import com.danielleitelima.resume.foundation.presentation.foundation.LocalNavHostController
import com.danielleitelima.resume.foundation.presentation.foundation.Screen
import com.danielleitelima.resume.foundation.presentation.foundation.theme.AppTheme
import com.danielleitelima.resume.home.presentation.foundation.registerHomeRoutes
import com.danielleitelima.resume.home.presentation.screen.home.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            MainScreenContent(
                navController = navController,
                startDestination = HomeScreen
            )
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun MainScreenContent(
        navController: NavHostController,
        startDestination: Screen,
    ) {
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
                                startDestination = startDestination.route.uri,
                            ) {
                                registerHomeRoutes()
                                registerChatRoutes()
                            }
                        }
                    }
                }
            }
        }
    }
}