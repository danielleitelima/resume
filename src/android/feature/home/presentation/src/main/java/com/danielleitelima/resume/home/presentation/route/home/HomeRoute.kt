package com.danielleitelima.resume.home.presentation.route.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import com.danielleitelima.resume.foundation.presentation.foundation.BaseRoute
import com.danielleitelima.resume.foundation.presentation.foundation.getKoinInstance
import com.danielleitelima.resume.foundation.presentation.foundation.rememberViewModel

object HomeRoute : BaseRoute() {

    @Composable
    override fun Content(backStackEntry: NavBackStackEntry) {
        val viewModel: HomeViewModel = rememberViewModel { getKoinInstance() }
        val state by viewModel.state.collectAsState()

        state.resume?.let { HomeScreen(it) }
    }
}
