package com.danielleitelima.resume.chat.presentation.route.creation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import com.danielleitelima.resume.foundation.presentation.foundation.getKoinInstance
import com.danielleitelima.resume.foundation.presentation.foundation.navigation.BaseRoute
import com.danielleitelima.resume.foundation.presentation.foundation.navigation.ChatRoute
import com.danielleitelima.resume.foundation.presentation.foundation.rememberViewModel

object CreationRoute : BaseRoute() {

    override val route: String = ChatRoute.Creation.name

    @Composable
    override fun Content(backStackEntry: NavBackStackEntry) {
        val viewModel: CreationViewModel = rememberViewModel { getKoinInstance() }
        val state by viewModel.state.collectAsState()

        CreationScreen()
    }
}
