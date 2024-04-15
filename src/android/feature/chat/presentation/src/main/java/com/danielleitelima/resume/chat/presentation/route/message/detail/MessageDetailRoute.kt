package com.danielleitelima.resume.chat.presentation.route.message.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import com.danielleitelima.resume.foundation.presentation.foundation.getKoinInstance
import com.danielleitelima.resume.foundation.presentation.foundation.navigation.BaseRoute
import com.danielleitelima.resume.foundation.presentation.foundation.navigation.ChatRoute
import com.danielleitelima.resume.foundation.presentation.foundation.rememberViewModel

object MessageDetailRoute : BaseRoute() {

    override val route: String = ChatRoute.MessageDetail.name

    @Composable
    override fun Content(backStackEntry: NavBackStackEntry) {
        val viewModel: MessageDetailViewModel = rememberViewModel { getKoinInstance() }
        val state by viewModel.state.collectAsState()

        MessageDetailScreen()
    }
}
