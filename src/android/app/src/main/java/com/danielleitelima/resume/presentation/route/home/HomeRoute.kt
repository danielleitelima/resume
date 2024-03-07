package com.danielleitelima.resume.presentation.route.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import com.danielleitelima.resume.presentation.foundation.BaseRoute
import com.danielleitelima.resume.presentation.foundation.getKoinInstance
import com.danielleitelima.resume.presentation.foundation.rememberViewModel

object HomeRoute : BaseRoute() {

    @Composable
    override fun Content(backStackEntry: NavBackStackEntry) {
        val viewModel: HomeViewModel = rememberViewModel { getKoinInstance() }
        val state by viewModel.state.collectAsState()

        state.resume?.let { HomeScreen(it) }
    }
}
