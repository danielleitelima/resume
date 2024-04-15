package com.danielleitelima.resume.chat.presentation.route.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.chat.presentation.R
import com.danielleitelima.resume.foundation.presentation.foundation.LocalNavHostController
import com.danielleitelima.resume.foundation.presentation.foundation.navigation.ChatRoute
import com.danielleitelima.resume.foundation.presentation.foundation.theme.Dimension

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val navController = LocalNavHostController.current

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(ChatRoute.Creation.name)
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
