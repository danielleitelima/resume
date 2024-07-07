package com.danielleitelima.resume.chat.presentation.screen.message.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import com.danielleitelima.resume.chat.domain.Message
import com.danielleitelima.resume.chat.domain.MessageOption
import com.danielleitelima.resume.chat.domain.OpenChat
import com.danielleitelima.resume.chat.domain.SentMessage
import com.danielleitelima.resume.chat.domain.Vocabulary
import com.danielleitelima.resume.chat.domain.Word
import com.danielleitelima.resume.chat.domain.WordMeaning
import com.danielleitelima.resume.chat.presentation.R
import com.danielleitelima.resume.chat.presentation.screen.component.toFormattedTime
import com.danielleitelima.resume.foundation.presentation.component.Clickable
import com.danielleitelima.resume.foundation.presentation.foundation.LocalNavHostController
import com.danielleitelima.resume.foundation.presentation.foundation.Screen
import com.danielleitelima.resume.foundation.presentation.foundation.TextToSpeechManager
import com.danielleitelima.resume.foundation.presentation.foundation.copyToClipboard
import com.danielleitelima.resume.foundation.presentation.foundation.getKoinInstance
import com.danielleitelima.resume.foundation.presentation.foundation.rememberViewModel
import com.danielleitelima.resume.foundation.presentation.foundation.theme.Dimension
import com.danielleitelima.resume.foundation.presentation.route.chat.MessageList
import kotlinx.coroutines.launch
import me.saket.extendedspans.ExtendedSpans
import me.saket.extendedspans.SquigglyUnderlineSpanPainter
import me.saket.extendedspans.drawBehind
import me.saket.extendedspans.rememberSquigglyUnderlineAnimator
import kotlin.time.Duration.Companion.seconds

object MessageListScreen : Screen {
    private const val ANIMATION_DURATION = 300

    override val route: MessageList
        get() = MessageList

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationGraphicsApi::class)
    @Composable
    override fun Content(backStackEntry: NavBackStackEntry) {
        val context = LocalContext.current
        val navController = LocalNavHostController.current

        val viewModel: MessageListViewModel = rememberViewModel { getKoinInstance() }
        val state by viewModel.state.collectAsState()

        val chatId = remember { route.getChatId(backStackEntry) }
        var showRollbackDialog by remember { mutableStateOf(false) }
        var openBottomSheet by remember { mutableStateOf(false) }
        var selectedSentMessageId by remember { mutableStateOf<String?>(null) }
        val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val coroutineScope = rememberCoroutineScope()

        var showMessageDialog by remember { mutableStateOf(false) }

        var showWordDialog by remember { mutableStateOf(false) }

        val messageSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = false,
        )

        val vocabularySheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = false
        )

        val textToSpeechManager: TextToSpeechManager = remember { getKoinInstance() }

        val isMessagePlaying = remember { mutableStateOf(false) }
        val isWordPlaying = remember { mutableStateOf(false) }

        var selectedVocabulary = remember { mutableStateOf<Vocabulary?>(null) }

        LaunchedEffect(chatId) {
            if (chatId != null) {
                viewModel.setEvent(MessageListContract.Event.LoadChat(chatId))
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        val show = selectedSentMessageId == null

                        AnimatedVisibility(
                            visible = show,
                            enter = fadeIn(animationSpec = tween(ANIMATION_DURATION)),
                            exit = fadeOut(animationSpec = tween(ANIMATION_DURATION))
                        ) {
                            Text(
                                maxLines = 1,
                                text = state.openChat?.title.orEmpty(),
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    },
                    navigationIcon = {
                        val icon =
                            AnimatedImageVector.animatedVectorResource(R.drawable.ic_close_to_arrow_back)

                        val hasSelectedMessage = selectedSentMessageId != null

                        IconButton(
                            onClick = {
                                if (hasSelectedMessage) {
                                    selectedSentMessageId = null
                                    return@IconButton
                                }
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                painter = rememberAnimatedVectorPainter(
                                    icon,
                                    hasSelectedMessage.not()
                                ),
                                contentDescription = stringResource(R.string.content_description_back_or_close),
                                modifier = Modifier.size(Dimension.Icon.dp),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    actions = {
                        val show = selectedSentMessageId != null
                        if (show.not()) return@TopAppBar
                        val scale = animateFloatAsState(
                            if (show) 1f else 0f,
                            label = stringResource(R.string.animation_label_scale)
                        )

                        IconButton(
                            onClick = {
                                showRollbackDialog = true
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_rewind),
                                contentDescription = stringResource(R.string.content_description_undo),
                                modifier = Modifier
                                    .size(Dimension.Icon.dp)
                                    .scale(scale.value),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        IconButton(
                            onClick = {
                                val messageContent = state.openChat?.history?.find {
                                    it.messageId == selectedSentMessageId
                                }?.content.orEmpty()

                                context.copyToClipboard(
                                    messageContent,
                                    context.getString(R.string.message_list_alert_content_copy)
                                )
                                selectedSentMessageId = null
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_content_copy),
                                contentDescription = stringResource(R.string.content_description_copy),
                                modifier = Modifier
                                    .size(Dimension.Icon.dp)
                                    .scale(scale.value),
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
            content = { paddingValues ->
                val openChat = state.openChat

                if (state.isLoading || openChat == null) {
                    return@Scaffold
                }

                if (state.openChat?.history.orEmpty().isEmpty()) {
                    EmptyChatView(
                        modifier = Modifier.padding(paddingValues)
                    )
                    return@Scaffold
                }

                MessageListView(
                    modifier = Modifier.padding(paddingValues),
                    selectedMessageId = selectedSentMessageId,
                    openChat = openChat,
                    onMessageSelected = {
                        selectedSentMessageId = null
                        showMessageDialog = true
                        viewModel.setEvent(MessageListContract.Event.SelectMessage(it))
                    },
                    onMessageLongPressed = {
                        selectedSentMessageId = it
                    }
                )
            },
            bottomBar = {
                val isLoading = state.messageOptions == null

                BottomBar(
                    enabled = isLoading.not(),
                    onClick = {
                        openBottomSheet = true
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    }
                )
            }
        )

        if (showRollbackDialog) {
            RollbackDialog(
                onConfirm = {
                    viewModel.setEvent(
                        MessageListContract.Event.RollbackToMessage(selectedSentMessageId.orEmpty())
                    )
                },
                onDismiss = {
                    selectedSentMessageId = null
                    showRollbackDialog = false
                }
            )
        }

        if (openBottomSheet) {
            MessageOptionModal(
                bottomSheetState = bottomSheetState,
                messageOptions = state.messageOptions.orEmpty(),
                onDismiss = {
                    openBottomSheet = false
                },
                onOptionSelected = {
                    viewModel.setEvent(MessageListContract.Event.SelectMessageOption(it))

                    coroutineScope.launch {
                        bottomSheetState.hide()
                        openBottomSheet = false
                    }
                },
                onStay = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                        openBottomSheet = false
                    }
                },
                onExplore = {
                    navController.popBackStack()
                }
            )
        }

        if (showMessageDialog) {
            val dependencyVocabulary = state.selectedMessage?.vocabularies?.firstOrNull {
                it.dependency == selectedVocabulary.value?.id
            }

            MessageModal(
                sheetState = messageSheetState,
                message = state.selectedMessage,
                isPlaying = isMessagePlaying,
                word = state.selectedWord,
                selectedVocabulary = selectedVocabulary.value,
                dependencyVocabulary = dependencyVocabulary,
                onDismiss = {
                    showMessageDialog = false
                    viewModel.setEvent(MessageListContract.Event.SelectWord(null))
                    selectedVocabulary.value = null
                },
                onWordSelected = {
                    val clickedVocabulary =
                        state.selectedMessage?.vocabularies?.find { vocabulary ->
                            val beginOffset = vocabulary.beginOffset

                            beginOffset <= it.toInt() && beginOffset + vocabulary.content.length >= it.toInt()
                        }

                    clickedVocabulary?.let { vocabulary ->
                        val word = vocabulary.word ?: return@let
                        coroutineScope.launch {
                            messageSheetState.partialExpand()
                            viewModel.setEvent(MessageListContract.Event.SelectWord(word))
                            selectedVocabulary.value = vocabulary
                        }
                    }
                }
            )
        }

//        if (showWordDialog) {
//            textToSpeechManager.stopSpeaking()
//            isMessagePlaying.value = false
//
//            WordModal(
//                modifier = Modifier.verticalScroll(rememberScrollState()),
//                bottomSheetState = vocabularySheetState,
//                word = state.selectedWord,
//                isPlaying = isWordPlaying
//            ){
//                coroutineScope.launch {
//                    messageSheetState.show()
//                }
//
//                showWordDialog = false
//                textToSpeechManager.stopSpeaking()
//                isWordPlaying.value = false
//            }
//        }
    }
}

@Composable
private fun MessageListView(
    modifier: Modifier = Modifier,
    selectedMessageId: String?,
    openChat: OpenChat,
    onMessageSelected: (String) -> Unit,
    onMessageLongPressed: (String) -> Unit,
) {
    val lastIndex =
        if (openChat.history.isEmpty()) 0
        else openChat.history.size - 1

    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = lastIndex
    )

    val items = openChat.history

    LaunchedEffect(key1 = openChat.history) {
        listState.animateScrollToItem(index = lastIndex)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = listState
    ) {
        item {
            Spacer(modifier = Modifier.size(Dimension.Spacing.M.dp))
        }
        items(items.size) { sentMessageId ->
            val sentMessage = items[sentMessageId]

            val isSelected = selectedMessageId == sentMessage.messageId

            SentMessageItem(
                sentMessage = sentMessage,
                isSelected = isSelected,
                onClick = onMessageSelected,
                onLongPress = onMessageLongPressed
            )

            Spacer(modifier = Modifier.size(Dimension.Spacing.S.dp))
        }
        item {
            Spacer(modifier = Modifier.size(Dimension.Spacing.XXS.dp))
        }
    }
}

@Composable
private fun EmptyChatView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Dimension.Spacing.L.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.size(130.dp))
        Icon(
            painter = painterResource(id = R.drawable.illustration_coiled_arrow),
            contentDescription = stringResource(R.string.content_description_coiled_arrow),
            modifier = Modifier.size(200.dp),
            tint = MaterialTheme.colorScheme.outlineVariant
        )
        Spacer(modifier = Modifier.size(Dimension.Spacing.XS.dp))
        Text(
            text = stringResource(R.string.message_list_empty_description),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
private fun BottomBar(
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(Dimension.Spacing.M.dp)
    ) {
        val buttonColor =
            if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .clip(RoundedCornerShape(Dimension.CornerRadius.dp))
                .clickable {
                    if (enabled.not()) return@clickable
                    onClick()
                },
        ) {
            Row(
                modifier = Modifier
                    .padding(Dimension.Spacing.S.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_send),
                    contentDescription = stringResource(R.string.content_description_send),
                    modifier = Modifier.size(Dimension.Icon.dp),
                    tint = buttonColor
                )
                Spacer(modifier = Modifier.size(Dimension.Spacing.XS.dp))
                Text(
                    text = stringResource(R.string.message_list_label_option),
                    style = MaterialTheme.typography.labelLarge,
                    color = buttonColor,
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MessageOptionModal(
    modifier: Modifier = Modifier,
    bottomSheetState: SheetState,
    messageOptions: List<MessageOption>,
    onDismiss: () -> Unit = {},
    onOptionSelected: (String) -> Unit = {},
    onStay: () -> Unit = {},
    onExplore: () -> Unit = {},
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
    ) {
        if (messageOptions.isEmpty()) {
            CompletedChatView(
                onExplore = onExplore,
                onStay = onStay
            )
        } else {
            MessageOptionList(
                messageOptions = messageOptions.orEmpty(),
                onOptionSelected = onOptionSelected
            )
        }
    }
}

@Composable
private fun MessageOptionList(
    messageOptions: List<MessageOption>,
    onOptionSelected: (String) -> Unit,
) {
    var selectedMessageOptionId by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.padding(horizontal = Dimension.Spacing.L.dp),
        horizontalAlignment = Alignment.End
    ) {
        messageOptions.forEach {
            MessageOptionItem(
                messageOption = it,
                isSelected = selectedMessageOptionId == it.id,
            ) {
                selectedMessageOptionId = if (selectedMessageOptionId == it.id) "" else it.id
            }

            Spacer(modifier = Modifier.size(Dimension.Spacing.S.dp))
        }
        Spacer(modifier = Modifier.size(Dimension.Spacing.S.dp))

        SendButton(
            enabled = selectedMessageOptionId.orEmpty().isNotEmpty(),
            onClick = {
                onOptionSelected(selectedMessageOptionId.orEmpty())
            },
        )

        Spacer(modifier = Modifier.size(Dimension.Spacing.L.dp))
    }
}

@Composable
private fun CompletedChatView(
    onExplore: () -> Unit,
    onStay: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(
                bottom = Dimension.Spacing.L.dp,
                start = Dimension.Spacing.L.dp,
                end = Dimension.Spacing.L.dp
            )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.illustration_chat_completed),
            contentDescription = stringResource(R.string.content_description_chat_completed),
            modifier = Modifier.size(150.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.size(Dimension.Spacing.L.dp))
        Column(
            modifier = Modifier.padding(horizontal = Dimension.Spacing.L.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.message_list_chat_completed_title),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.size(Dimension.Spacing.S.dp))
            Text(
                text = stringResource(R.string.message_list_chat_completed_description),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.size(Dimension.Spacing.XL.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = onStay,
            ) {
                Text(
                    text = stringResource(R.string.message_list_chat_completed_button_stay),
                    style = MaterialTheme.typography.labelLarge,
                )
            }

            Button(
                onClick = onExplore,
            ) {
                Text(
                    text = stringResource(R.string.message_list_chat_completed_button_explore),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

@Composable
private fun SendButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_send),
            contentDescription = stringResource(R.string.content_description_send),
            modifier = Modifier
                .size(Dimension.Icon.dp),
        )
        Spacer(modifier = Modifier.size(Dimension.Spacing.XS.dp))
        Text(
            text = stringResource(R.string.message_list_button_send),
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Composable
private fun MessageOptionItem(
    messageOption: MessageOption,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Clickable(onClick = onClick) {
        val border = if (isSelected) BorderStroke(
            Dimension.Stroke.dp,
            MaterialTheme.colorScheme.primary
        ) else null

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(Dimension.CornerRadius.dp),
            border = border,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimension.Spacing.M.dp,
                        vertical = Dimension.Spacing.S.dp
                    ),
                text = messageOption.content,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SentMessageItem(
    modifier: Modifier = Modifier,
    sentMessage: SentMessage,
    isSelected: Boolean,
    onClick: (String) -> Unit,
    onLongPress: (String) -> Unit = {},
) {
    val alignment = if (sentMessage.isUserSent) Alignment.CenterEnd else Alignment.CenterStart
    val columnAlignment = if (sentMessage.isUserSent) Alignment.End else Alignment.Start

    val containerColor =
        if (sentMessage.isUserSent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
    val contentColor =
        if (sentMessage.isUserSent) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer

    val paddingStart = if (sentMessage.isUserSent) 68.dp else 0.dp
    val paddingEnd = if (sentMessage.isUserSent) 0.dp else 68.dp

    val padding = PaddingValues(start = paddingStart, end = paddingEnd)

    val shape =
        if (sentMessage.isUserSent)
            RoundedCornerShape(
                topStart = Dimension.CornerRadius.dp,
                topEnd = 0.dp,
                bottomEnd = Dimension.CornerRadius.dp,
                bottomStart = Dimension.CornerRadius.dp
            )
        else
            RoundedCornerShape(
                topStart = 0.dp,
                topEnd = Dimension.CornerRadius.dp,
                bottomEnd = Dimension.CornerRadius.dp,
                bottomStart = Dimension.CornerRadius.dp
            )

    val haptic = LocalHapticFeedback.current

    val selectionColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.16f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                indication = rememberRipple(color = selectionColor),
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    onClick(sentMessage.messageId)
                },
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onLongPress(sentMessage.messageId)
                }
            )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = Dimension.Spacing.L.dp, vertical = Dimension.Spacing.XS.dp)
                .padding(padding)
                .align(alignment),
            horizontalAlignment = columnAlignment
        ) {
            Box(
                modifier = Modifier
                    .clip(shape)
                    .background(containerColor)
                    .widthIn(min = 80.dp),
            ) {
                Text(
                    modifier = Modifier
                        .padding(Dimension.Spacing.S.dp)
                        .align(alignment),
                    text = sentMessage.content,
                    style = MaterialTheme.typography.bodyLarge,
                    color = contentColor,
                )
            }
            Spacer(modifier = Modifier.size(Dimension.Spacing.XS.dp))
            Text(
                text = sentMessage.timestamp.toFormattedTime(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        if (isSelected) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(selectionColor)
            )
        }
    }
}

@Composable
private fun RollbackDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(stringResource(R.string.rollback_dialog_title)) },
        text = { Text(stringResource(R.string.rollback_dialog_description)) },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.rollback_dialog_button_confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text(stringResource(R.string.rollback_dialog_button_cancel))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MessageModal(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    message: Message?,
    word: Word?,
    selectedVocabulary: Vocabulary?,
    dependencyVocabulary: Vocabulary?,
    isPlaying: MutableState<Boolean>,
    onDismiss: () -> Unit = {},
    onWordSelected: (String) -> Unit = {},
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(
                    start = Dimension.Spacing.L.dp,
                    end = Dimension.Spacing.L.dp,
                    top = Dimension.Spacing.M.dp,
                )
                .verticalScroll(rememberScrollState())
            ,
        ) {
            ContentSection(
                message = message,
                word = word,
                onWordSelected = onWordSelected,
                isPlaying = isPlaying
            )

            Spacer(modifier = Modifier.height(Dimension.Spacing.XL.dp))

            AnimatedContent(
                targetState = word,
                transitionSpec = {
                    fadeIn(animationSpec = tween(50)) togetherWith fadeOut(animationSpec = tween(50))
                }
            ) { word ->
                if (word == null) return@AnimatedContent

                Column {
                    TextDivider(
                        text = "Main meaning",
                        backgroundColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    )

                    Spacer(modifier = Modifier.height(Dimension.Spacing.M.dp))

                    val inlineContentId = "icon"

                    val annotatedString = buildAnnotatedString {
                        append(word.content)
                        appendInlineContent(inlineContentId, "[icon]")
                    }

                    val inlineContent = mapOf(
                        inlineContentId to InlineTextContent(
                            Placeholder(36.sp, 32.sp, PlaceholderVerticalAlign.TextCenter)
                        ) {
                            TextToSpeechButton(
                                modifier = Modifier.padding(start = Dimension.Spacing.XXS.dp),
                                text = word.content,
                                isPlaying = isPlaying
                            )
                        }
                    )

                    Text(
                        text = annotatedString,
                        inlineContent = inlineContent,
                        lineHeight = 32.sp,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(modifier = Modifier.height(Dimension.Spacing.M.dp))

                    val mainMeaning = word.meanings.firstOrNull {
                        it.partsOfSpeech == selectedVocabulary?.partOfSpeech
                    } ?: word.meanings.firstOrNull()

                    if (mainMeaning != null) {
                        MeaningItem(meaning = mainMeaning)
                        Spacer(modifier = Modifier.height(Dimension.Spacing.M.dp))
                    }

                    TextDivider(
                        text = "Syntax",
                        backgroundColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    )

                    ListItem(
                        modifier = Modifier.padding(bottom = Dimension.Spacing.XS.dp),
                        content = selectedVocabulary?.case.orEmpty()
                    )

                    ListItem(
                        modifier = Modifier.padding(bottom = Dimension.Spacing.XS.dp),
                        content = selectedVocabulary?.aspect.orEmpty()
                    )

                    ListItem(
                        modifier = Modifier.padding(bottom = Dimension.Spacing.XS.dp),
                        content = selectedVocabulary?.gender.orEmpty()
                    )

                    ListItem(
                        modifier = Modifier.padding(bottom = Dimension.Spacing.XS.dp),
                        content = selectedVocabulary?.form.orEmpty()
                    )

                    ListItem(
                        modifier = Modifier.padding(bottom = Dimension.Spacing.XS.dp),
                        content = selectedVocabulary?.number.orEmpty()
                    )

                    ListItem(
                        modifier = Modifier.padding(bottom = Dimension.Spacing.XS.dp),
                        content = selectedVocabulary?.person.orEmpty()
                    )

                    ListItem(
                        modifier = Modifier.padding(bottom = Dimension.Spacing.XS.dp),
                        content = selectedVocabulary?.tense.orEmpty()
                    )

                    ListItem(
                        modifier = Modifier.padding(bottom = Dimension.Spacing.XS.dp),
                        content = selectedVocabulary?.voice.orEmpty()
                    )

                    ListItem(
                        modifier = Modifier.padding(bottom = Dimension.Spacing.M.dp),
                        content = selectedVocabulary?.mood.orEmpty()
                    )

                    if (selectedVocabulary != null && dependencyVocabulary != null && selectedVocabulary.id != dependencyVocabulary.id) {
                        VocabularyDependencySection(
                            selectedVocabulary = selectedVocabulary,
                            dependencyVocabulary = dependencyVocabulary,
                        )
                        Spacer(modifier = Modifier.height(Dimension.Spacing.M.dp))
                    }

                    val hasOtherMeanings = word.meanings.size > 1

                    if (hasOtherMeanings) {
                        TextDivider(
                            text = "Other meanings",
                            backgroundColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        )

                        Spacer(modifier = Modifier.height(Dimension.Spacing.M.dp))

                        word.meanings.forEachIndexed { index, meaning ->
                            if (mainMeaning?.id == meaning.id) return@forEachIndexed

                            MeaningItem(meaning = meaning)
                            Spacer(modifier = Modifier.height(Dimension.Spacing.M.dp))
                            if (index < word.meanings.size - 1) {
                                Spacer(modifier = Modifier.height(Dimension.Spacing.M.dp))
                            }
                        }
                    } else{
                        Spacer(modifier = Modifier.height(Dimension.Spacing.M.dp))
                    }
                }
            }

        }
    }
}

@Composable
private fun VocabularyDependencySection(
    selectedVocabulary: Vocabulary,
    dependencyVocabulary: Vocabulary,
){
    TextDivider(
        text = "Dependency",
        backgroundColor = MaterialTheme.colorScheme.surfaceContainerLow,
    )

    Spacer(modifier = Modifier.height(Dimension.Spacing.M.dp))

    Column {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = selectedVocabulary.dependencyType.orEmpty(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(Dimension.Spacing.XS.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            VocabularyCard(
                modifier = Modifier.weight(1f),
                vocabulary = selectedVocabulary,
            )
            Spacer(modifier = Modifier.width(Dimension.Spacing.M.dp))
            Text(text = "→", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.width(Dimension.Spacing.M.dp))
            VocabularyCard(
                modifier = Modifier.weight(1f),
                vocabulary = dependencyVocabulary,
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        }
    }
}

@Composable
private fun VocabularyCard(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    vocabulary: Vocabulary?,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(Dimension.CornerRadius.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
        ),
    ) {
        Column(
            modifier = Modifier.padding(Dimension.Spacing.M.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = vocabulary?.content.orEmpty(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun ListItem(
    modifier: Modifier = Modifier,
    content: String,
) {
    if (content.lowercase().contains("unknown")) return

    Row(
        modifier = modifier,
    ) {
        Text(
            text = "•",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.size(Dimension.Spacing.XXS.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ContentSection(
    modifier: Modifier = Modifier,
    message: Message?,
    word: Word? = null,
    isPlaying: MutableState<Boolean>,
    onWordSelected: (String) -> Unit = {},
) {
    val inlineContentId = "icon"

    val messageAnnotatedString = buildAnnotatedString {
        append(message?.content.orEmpty())
        message?.vocabularies?.forEach { vocabulary ->
            if (vocabulary.word == null) return@forEach

            val beginOffset = vocabulary.beginOffset

            val color = if (vocabulary.word == word?.id) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.secondary
            }

            addStyle(
                style = SpanStyle(
                    textDecoration = TextDecoration.Underline,
                    color = color
                ),
                start = beginOffset,
                end = beginOffset + vocabulary.content.length
            )
        }
        appendInlineContent(inlineContentId, "[icon]")
    }

    val inlineContent = mapOf(
        inlineContentId to InlineTextContent(
            Placeholder(36.sp, 32.sp, PlaceholderVerticalAlign.TextCenter)
        ) {
            TextToSpeechButton(
                modifier = Modifier.padding(start = Dimension.Spacing.XXS.dp),
                text = message?.content.orEmpty(),
                isPlaying = isPlaying
            )
        }
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExtendedSpansText(
            text = messageAnnotatedString,
            inlineContent = inlineContent,
            onClick = onWordSelected
        )
        Spacer(modifier = Modifier.size(Dimension.Spacing.M.dp))
        MaskedTextWithIcon(
            modifier = Modifier.fillMaxWidth(),
            text = message?.translation.orEmpty(),
        )
    }
}

@Composable
fun MaskedTextWithIcon(
    modifier: Modifier = Modifier,
    text: String,
) {
    var isMasked by remember { mutableStateOf(true) }

    val icon = if (isMasked) R.drawable.ic_hide else R.drawable.ic_show

    val inlineContentId = "icon"

    val annotatedString = buildAnnotatedString {
        text.forEachIndexed { index, character ->
            if (isMasked) {
                withStyle(
                    style = SpanStyle(
                        background = MaterialTheme.colorScheme.surfaceVariant,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    append(character)
                }
            } else {
                append(character)
            }
        }
        appendInlineContent(inlineContentId, "[icon]")
    }

    val inlineContent = mapOf(
        inlineContentId to InlineTextContent(
            Placeholder(28.sp, 24.sp, PlaceholderVerticalAlign.TextCenter)
        ) {
            IconButton(
                modifier = Modifier.padding(start = Dimension.Spacing.XXS.dp),
                onClick = {
                    isMasked = isMasked.not()
                }
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        }
    )

    Text(
        textAlign = TextAlign.Center,
        lineHeight = 28.sp,
        modifier = modifier,
        text = annotatedString,
        inlineContent = inlineContent,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.outline,
    )
}


@Composable
private fun ExtendedSpansText(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onClick: (String) -> Unit = {},
) {
    val underlineAnimator = rememberSquigglyUnderlineAnimator(duration = 5.seconds)
    val extendedSpans = remember {
        ExtendedSpans(
            SquigglyUnderlineSpanPainter(
                width = 2.sp,
                wavelength = 8.sp,
                amplitude = 1.sp,
                bottomOffset = (-6).sp,
                animator = underlineAnimator
            )
        )
    }

    CustomClickableText(
        inlineContent = inlineContent,
        style = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            lineHeight = 42.sp
        ),
        modifier = modifier.drawBehind(extendedSpans),
        text = remember(text) {
            extendedSpans.extend(text)
        },
        onTextLayout = { result ->
            extendedSpans.onTextLayout(result)
        },
        onClick = { offset ->
            onClick.invoke(offset.toString())
        }
    )
}

@Composable
fun CustomClickableText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (Int) -> Unit,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
) {
    val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }
    val pressIndicator = Modifier.pointerInput(onClick) {
        detectTapGestures { pos ->
            layoutResult.value?.let { layoutResult ->
                onClick(layoutResult.getOffsetForPosition(pos))
            }
        }
    }

    BasicText(
        text = text,
        modifier = modifier.then(pressIndicator),
        style = style,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
        inlineContent = inlineContent,
        onTextLayout = {
            layoutResult.value = it
            onTextLayout(it)
        }
    )
}

@Composable
private fun MeaningItem(
    meaning: WordMeaning,
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = meaning.content + " " + "[ " + meaning.partsOfSpeech + " ]",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(Dimension.Spacing.M.dp))

        meaning.definitions.forEach { definition ->
            Text(
                text = definition.content,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(Dimension.Spacing.XXS.dp))

            definition.examples.forEach { example ->
                val inlineContentId = "icon"

                val annotatedString = buildAnnotatedString {
                    append(example.content)
                    appendInlineContent(inlineContentId, "[icon]")
                }

                val inlineContent = mapOf(
                    inlineContentId to InlineTextContent(
                        Placeholder(32.sp, 32.sp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        TextToSpeechButton(
                            text = example.content,
                            isPlaying = remember { mutableStateOf(false) }
                        )
                    }
                )

                Text(
                    text = annotatedString,
                    inlineContent = inlineContent,
                    lineHeight = 32.sp,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Spacer(modifier = Modifier.height(Dimension.Spacing.XS.dp))
        }

        meaning.relatedWords.forEach { relatedWord ->
            ListItem(
                modifier = Modifier.padding(bottom = Dimension.Spacing.XS.dp),
                content = relatedWord.type + ": " + relatedWord.content
            )
        }
    }
}

@Composable
private fun TextToSpeechButton(
    modifier: Modifier = Modifier,
    text: String,
    isPlaying: MutableState<Boolean>,
) {
    val textToSpeechManager: TextToSpeechManager = remember { getKoinInstance() }

    LaunchedEffect(isPlaying.value) {
        if (isPlaying.value) {
            textToSpeechManager.startSpeaking(text) {
                isPlaying.value = false
            }
        } else {
            textToSpeechManager.stopSpeaking()
        }
    }

    IconButton(
        modifier = modifier,
        onClick = { isPlaying.value = isPlaying.value.not() }
    ) {
        val icon = if (isPlaying.value) {
            R.drawable.ic_stop
        } else {
            R.drawable.ic_volume_up
        }
        Icon(
            painter = painterResource(id = icon),
            contentDescription = stringResource(R.string.content_description_volume_up),
            modifier = Modifier.size(Dimension.Icon.dp),
            tint = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
private fun TextDivider(
    text: String,
    backgroundColor: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        HorizontalDivider(
            modifier = Modifier.align(Alignment.CenterStart),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .background(backgroundColor)
                .padding(horizontal = Dimension.Spacing.XXS.dp),
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
