package com.danielleitelima.resume.chat.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.danielleitelima.resume.chat.domain.ActiveChat
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
import com.danielleitelima.resume.foundation.presentation.route.chat.Home
import com.danielleitelima.resume.foundation.presentation.route.chat.MessageList

object HomeScreen : Screen {
    override val route: Route
        get() = Home

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(backStackEntry: NavBackStackEntry) {
        val viewModel: HomeViewModel = rememberViewModel { getKoinInstance() }
        val state by viewModel.state.collectAsState()

        val navController = LocalNavHostController.current

        var openBottomSheet by rememberSaveable { mutableStateOf(false) }
        val bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        val activeChats = state.activeChats

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(stringResource(R.string.feature_name))
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            openBottomSheet = true
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_info_outline),
                                contentDescription = stringResource(R.string.content_description_info),
                                modifier = Modifier.size(Dimension.Icon.dp),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = stringResource(R.string.content_description_back),
                                modifier = Modifier.size(Dimension.Icon.dp),
                                tint = MaterialTheme.colorScheme.surfaceContainer
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
                if (activeChats.isEmpty()){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .padding(horizontal = Dimension.Spacing.XXL.dp, vertical = 130.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.illustration_chatting),
                            contentDescription = stringResource(R.string.content_description_chatting),
                            modifier = Modifier.size(200.dp),
                            tint = MaterialTheme.colorScheme.outlineVariant
                        )
                        Spacer(modifier = Modifier.size(Dimension.Spacing.XS.dp))
                        Text(
                            text = stringResource(R.string.home_title),
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.outline
                        )
                        Spacer(modifier = Modifier.size(Dimension.Spacing.S.dp))
                        Text(
                            text = stringResource(R.string.home_description),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                } else {
                    ChatList(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        activeChats = activeChats
                    ){ chatId ->
                        navController.navigate(MessageList.routeWithArguments(chatId))
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Creation)
                    },
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_message),
                            contentDescription = stringResource(R.string.content_description_create_chat),
                            modifier = Modifier.size(Dimension.Icon.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                )
            }
        )

        if (openBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { openBottomSheet = false },
                sheetState = bottomSheetState,
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimension.Spacing.L.dp)
                        .padding(bottom = Dimension.Spacing.L.dp),
                ) {
                    Text(
                        text = stringResource(R.string.home_overlay_content),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatList(
    modifier: Modifier = Modifier,
    activeChats: List<ActiveChat>,
    onClick: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(Dimension.Spacing.XXS.dp))
        activeChats.forEach { chat ->
            ChatItem(chat){
                onClick(chat.id)
            }
        }
        Spacer(modifier = Modifier.height(Dimension.Spacing.XXL.dp))
    }
}


@Composable
private fun ChatItem(
    chat: ActiveChat,
    onClick: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 90.dp)
            .padding(horizontal = Dimension.Spacing.M.dp, vertical = Dimension.Spacing.S.dp)
            .stealthClickable { onClick(chat.id) }
        ,
    ) {
        InitialAvatar(name = chat.title)
        Spacer(modifier = Modifier.width(Dimension.Spacing.M.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = chat.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = chat.lastSentMessage?.content.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.width(Dimension.Spacing.M.dp))
        Text(
            text = "19:00 PM",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
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
