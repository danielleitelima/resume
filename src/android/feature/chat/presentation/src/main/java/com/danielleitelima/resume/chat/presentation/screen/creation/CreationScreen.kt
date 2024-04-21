package com.danielleitelima.resume.chat.presentation.screen.creation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.danielleitelima.resume.chat.presentation.R
import com.danielleitelima.resume.foundation.presentation.foundation.LocalNavHostController
import com.danielleitelima.resume.foundation.presentation.foundation.Route
import com.danielleitelima.resume.foundation.presentation.foundation.Screen
import com.danielleitelima.resume.foundation.presentation.foundation.getKoinInstance
import com.danielleitelima.resume.foundation.presentation.foundation.navigate
import com.danielleitelima.resume.foundation.presentation.foundation.rememberViewModel
import com.danielleitelima.resume.foundation.presentation.foundation.stealthClickable
import com.danielleitelima.resume.foundation.presentation.foundation.theme.Dimension
import com.danielleitelima.resume.foundation.presentation.route.chat.Creation
import com.danielleitelima.resume.foundation.presentation.route.chat.MessageList

object CreationScreen : Screen {
    override val route: Route
        get() = Creation

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(backStackEntry: NavBackStackEntry) {
        val viewModel: CreationViewModel = rememberViewModel { getKoinInstance() }
        val state by viewModel.state.collectAsState()

        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        val navController = LocalNavHostController.current

        val availableChats = state.availableChats

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(stringResource(R.string.creation_title))
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = stringResource(id = R.string.content_description_back),
                                modifier = Modifier.size(Dimension.Icon.dp),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    ),
                    scrollBehavior = scrollBehavior,
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    availableChats.forEach { availableChat ->
                        CreationItem(availableChat.title){
                            navController.navigate(MessageList)
                        }
                    }
                }
            },
        )
    }
}

@Composable
private fun CreationItem(
    name: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimension.Spacing.M.dp, vertical = Dimension.Spacing.S.dp)
            .stealthClickable { onClick() }
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        InitialAvatar(name = name)
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = Dimension.Spacing.M.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun InitialAvatar(name: String) {
    val parts = name.split(" ").mapNotNull { it.firstOrNull()?.toString() }
    val initials = if (parts.size > 1) {
        "${parts.first()}${parts.last()}"
    } else {
        parts.firstOrNull() ?: ""
    }

    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
