package com.danielleitelima.resume.chat.presentation.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.danielleitelima.resume.chat.domain.Language
import com.danielleitelima.resume.chat.domain.OpenChat
import com.danielleitelima.resume.chat.presentation.R
import com.danielleitelima.resume.chat.presentation.screen.component.toFormattedTime
import com.danielleitelima.resume.foundation.presentation.component.Clickable
import com.danielleitelima.resume.foundation.presentation.component.build
import com.danielleitelima.resume.foundation.presentation.component.shimmerEffect
import com.danielleitelima.resume.foundation.presentation.foundation.LocalNavHostController
import com.danielleitelima.resume.foundation.presentation.foundation.Route
import com.danielleitelima.resume.foundation.presentation.foundation.Screen
import com.danielleitelima.resume.foundation.presentation.foundation.TextToSpeechManager
import com.danielleitelima.resume.foundation.presentation.foundation.getKoinInstance
import com.danielleitelima.resume.foundation.presentation.foundation.navigate
import com.danielleitelima.resume.foundation.presentation.foundation.rememberViewModel
import com.danielleitelima.resume.foundation.presentation.foundation.stealthClickable
import com.danielleitelima.resume.foundation.presentation.foundation.theme.Dimension
import com.danielleitelima.resume.foundation.presentation.route.chat.Creation
import com.danielleitelima.resume.foundation.presentation.route.chat.Home
import com.danielleitelima.resume.foundation.presentation.route.chat.MessageList
import kotlinx.coroutines.launch
import java.util.Locale

object HomeScreen : Screen {
    override val route: Route
        get() = Home

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(backStackEntry: NavBackStackEntry) {
        val navController = LocalNavHostController.current

        val textToSpeechManager: TextToSpeechManager = remember { getKoinInstance() }
        val viewModel: HomeViewModel = rememberViewModel { getKoinInstance() }

        val state by viewModel.state.collectAsState()

        val coroutineScope = rememberCoroutineScope()

        var openBottomSheet by remember { mutableStateOf(false) }

        val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        val selectedTargetLanguage = remember {
            mutableStateOf(state.selectedTargetLanguage)
        }

        val selectedTranslationLanguage = remember {
            mutableStateOf(state.selectedTranslationLanguage)
        }

        LaunchedEffect(key1 = state.selectedTranslationLanguage, key2 = state.selectedTargetLanguage) {
            selectedTargetLanguage.value = state.selectedTargetLanguage
            selectedTranslationLanguage.value = state.selectedTranslationLanguage

            if (state.selectedTargetLanguage != null){
                textToSpeechManager.setLanguage(Locale(state.selectedTargetLanguage?.code.orEmpty().substring(0, 2)))
            }
        }

        LaunchedEffect(key1 = selectedTargetLanguage.value, key2 = selectedTranslationLanguage.value) {
            if (selectedTargetLanguage.value != null && selectedTranslationLanguage.value != null) {
                viewModel.setEvent(
                    HomeContract.Event.OnLanguagesSelected(
                        target = selectedTargetLanguage.value ?: return@LaunchedEffect,
                        translation = selectedTranslationLanguage.value ?: return@LaunchedEffect
                    )
                )
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(stringResource(R.string.feature_name))
                        }
                    },
                    actions = {
                        IconButton(
                            enabled = state.isLoading.not(),
                            onClick = {
                                openBottomSheet = true
                            }
                        ) {
                            val flagUrl = state.selectedTargetLanguage?.flagUrl

                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current).build(flagUrl),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .width(Dimension.Icon.dp)
                                    .clip(RoundedCornerShape(2.dp))
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = stringResource(R.string.content_description_back),
                                modifier = Modifier.size(Dimension.Icon.dp),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    ),
                    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                )
            },
            content = {
                val openChats = state.chats

                if (state.isLoading){
                    OpenChatListSkeleton(modifier = Modifier.fillMaxSize().padding(it))
                    return@Scaffold
                }

                if (openChats.isEmpty()){
                    EmptyChatList(modifier = Modifier.padding(it))
                } else {
                    OpenChatList(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        openChats = openChats
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
            LanguageSelectionDialog(
                bottomSheetState = bottomSheetState,
                selectedTargetLanguage = selectedTargetLanguage,
                selectedTranslationLanguage = selectedTranslationLanguage,
                translationLanguages = state.translationLanguages,
                targetLanguages = state.targetLanguages
            ){
                coroutineScope.launch {
                    bottomSheetState.hide()
                    openBottomSheet = false
                }
            }
        }
    }
}

@Composable
private fun EmptyChatList(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LanguageSelectionDialog(
    modifier: Modifier = Modifier,
    bottomSheetState: SheetState,
    selectedTargetLanguage: MutableState<Language?>,
    selectedTranslationLanguage: MutableState<Language?>,
    translationLanguages: List<Language>,
    targetLanguages: List<Language>,
    onDismiss: () -> Unit = {},
){
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
    ) {
        val isSelectionEnabled = selectedTargetLanguage.value != null && selectedTranslationLanguage.value != null

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimension.Spacing.XL.dp),
                text = stringResource(R.string.language_selection_dialog_target_description),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(Dimension.Spacing.L.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.width(Dimension.Spacing.L.dp))
                targetLanguages.forEach { language ->
                    LanguageItem(
                        language = language,
                        selectedLanguage = selectedTargetLanguage.value
                    ){
                        selectedTargetLanguage.value = language
                    }
                    Spacer(modifier = Modifier.width(Dimension.Spacing.M.dp))
                }
            }
            Spacer(modifier = Modifier.height(Dimension.Spacing.XL.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimension.Spacing.XL.dp),
                text = stringResource(R.string.language_selection_dialog_translation_description),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(Dimension.Spacing.L.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.width(Dimension.Spacing.L.dp))
                translationLanguages.forEach { language ->
                    LanguageItem(
                        language = language,
                        selectedLanguage = selectedTranslationLanguage.value
                    ){
                        selectedTranslationLanguage.value = language
                    }
                    Spacer(modifier = Modifier.width(Dimension.Spacing.M.dp))
                }
            }
            Spacer(modifier = Modifier.height(Dimension.Spacing.L.dp))
            Button(
                modifier = Modifier
                    .padding(horizontal = Dimension.Spacing.L.dp)
                    .align(Alignment.End),
                enabled = isSelectionEnabled,
                onClick = onDismiss,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = null,
                    modifier = Modifier
                        .size(Dimension.Icon.dp),
                )
                Spacer(modifier = Modifier.size(Dimension.Spacing.XS.dp))
                Text(
                    text = stringResource(R.string.language_selection_dialog_button_confirm),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
            Spacer(modifier = Modifier.size(Dimension.Spacing.XL.dp))
        }
    }
}

@Composable
private fun LanguageItem(
    language: Language,
    selectedLanguage: Language? = null,
    onClick: () -> Unit = {}
) {
    val isSelected = if (selectedLanguage != null) {
        language.code == selectedLanguage.code
    } else {
        language.selected
    }

    val border = if (isSelected) BorderStroke(Dimension.Stroke.dp, MaterialTheme.colorScheme.primary) else null

    Clickable(onClick = onClick) {
        Card(
            modifier = Modifier
                .widthIn(min = 130.dp)
            ,
            shape = RoundedCornerShape(Dimension.CornerRadius.dp),
            border = border,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = Dimension.Spacing.L.dp, horizontal = Dimension.Spacing.S.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).build(language.flagUrl),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .size(Dimension.Flag.dp)
                        .clip(RoundedCornerShape(2.dp))
                )
                Spacer(modifier = Modifier.height(Dimension.Spacing.XS.dp))
                Text(
                    text = language.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun OpenChatList(
    modifier: Modifier = Modifier,
    openChats: List<OpenChat>,
    onClick: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(Dimension.Spacing.XXS.dp))
        openChats.forEach { chat ->
            OpenChatItem(chat){
                onClick(chat.id)
            }
        }
        Spacer(modifier = Modifier.height(Dimension.Spacing.XXL.dp))
    }
}

@Composable
private fun OpenChatListSkeleton(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(Dimension.Spacing.XXS.dp))
        repeat(15) {
            OpenChatItemSkeleton()
        }
        Spacer(modifier = Modifier.height(Dimension.Spacing.XXL.dp))
    }
}


@Composable
private fun OpenChatItem(
    chat: OpenChat ,
    onClick: (String) -> Unit = {}
) {
    val lastSentMessage = chat.history.maxByOrNull { it.timestamp }

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
                modifier = Modifier,
                text = chat.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier,
                text = lastSentMessage?.content.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.width(Dimension.Spacing.M.dp))
        Text(
            modifier = Modifier,
            text = lastSentMessage?.timestamp.toFormattedTime(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun OpenChatItemSkeleton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 90.dp)
            .padding(horizontal = Dimension.Spacing.M.dp, vertical = Dimension.Spacing.S.dp)
        ,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .shimmerEffect(),
        )
        Spacer(modifier = Modifier.width(Dimension.Spacing.M.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .shimmerEffect(),
                text = "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.height(Dimension.Spacing.XXS.dp))
            Text(
                modifier = Modifier
                    .widthIn(min = 160.dp)
                    .shimmerEffect(),
                text = "",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Spacer(modifier = Modifier.width(Dimension.Spacing.M.dp))
        Text(
            modifier = Modifier
                .widthIn(min = 60.dp)
                .shimmerEffect(),
            text = "",
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Composable
private fun InitialAvatar(name: String) {
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