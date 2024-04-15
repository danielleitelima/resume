package com.danielleitelima.resume.home.presentation.route.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import com.danielleitelima.resume.foundation.presentation.foundation.getKoinInstance
import com.danielleitelima.resume.foundation.presentation.foundation.navigation.BaseRoute
import com.danielleitelima.resume.foundation.presentation.foundation.navigation.HomeRoute
import com.danielleitelima.resume.foundation.presentation.foundation.rememberViewModel

object HomeRoute : BaseRoute() {

    override val route: String = HomeRoute.name

    @Composable
    override fun Content(backStackEntry: NavBackStackEntry) {
        val viewModel: HomeViewModel = rememberViewModel { getKoinInstance() }
        val state by viewModel.state.collectAsState()

        state.resume?.let { HomeScreen(it) }
    }
}
