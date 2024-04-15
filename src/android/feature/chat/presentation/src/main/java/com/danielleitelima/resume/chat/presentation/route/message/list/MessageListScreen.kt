package com.danielleitelima.resume.chat.presentation.route.message.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.chat.domain.SentMessage
import com.danielleitelima.resume.chat.presentation.R
import com.danielleitelima.resume.foundation.presentation.foundation.LocalNavHostController
import com.danielleitelima.resume.foundation.presentation.foundation.copyToClipboard
import com.danielleitelima.resume.foundation.presentation.foundation.navigation.ChatRoute
import com.danielleitelima.resume.foundation.presentation.foundation.theme.Dimension
import java.util.Calendar

const val ANIMATION_DURATION = 300

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationGraphicsApi::class)
@Composable
fun MessageListScreen() {
    val navController = LocalNavHostController.current

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var edgeToEdgeEnabled by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    var selectedMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    // TODO: Replace with real messages
    val mockedSentMessages = listOf(
        SentMessage("1", "Hey! Long time no see. How have you been?", true, 1713131163418),
        SentMessage("2", "Hi! I've been doing well. Busy, but good. How about you?", false, 1713131173418),
        SentMessage("3", "I'm good too, just been caught up with work. What have you been up to?", true, 1713131183418),
        SentMessage("4", "Mostly working. Just wrapped up a big project. I'm taking some time off now. Any plans for the weekend?", false, 1713131193418),
        SentMessage("5", "Actually, I'm planning a short trip to the mountains. Ever been to the Rockies?", true, 1713131203418),
        SentMessage("6", "Yes, I went last year. The view was breathtaking. Make sure to check out the Echo Lake Park if you can.", false, 1713131213418),
        SentMessage("7", "Sounds great! I'll definitely add that to my list. Have you got any stories from your trip?", true, 1713131223418),
        SentMessage("8", "Plenty! One funny thing happened when we were hiking. We had just reached a clearing, and suddenly a squirrel decided my backpack was a great place to explore. Spent 20 minutes trying to coax it out with trail mix.", false, 1713131233418)
    )

    val mockedMessageOptions = listOf(
        "Haha, that sounds like quite an adventure! Animals always bring unexpected moments. 😄",
        "Wow, I can't imagine dealing with a squirrel in my backpack! Did you manage to get any photos of that?",
        "That's hilarious! 😂 It must have been quite a scene. What else did you do on your trip?",
        "Squirrels can be so cheeky! Thanks for the tip, I'll be sure to keep my snacks sealed up tight.",
        "Sounds like you made some memorable moments. What’s the best spot you would recommend visiting?"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // TODO: Replace with real chat name
                    val mockedChatName = "Lili"

                    val show = selectedMessage == null

                    AnimatedVisibility(
                        visible = show,
                        enter = fadeIn(animationSpec = tween(ANIMATION_DURATION)),
                        exit = fadeOut(animationSpec = tween(ANIMATION_DURATION))
                    ) {
                        Text(mockedChatName)
                    }
                },
                navigationIcon = {
                    val icon = AnimatedImageVector.animatedVectorResource(R.drawable.ic_close_to_arrow_back)

                    val hasSelectedMessage = selectedMessage != null

                    IconButton(onClick = {
                        if (hasSelectedMessage) {
                            selectedMessage = null
                            return@IconButton
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = rememberAnimatedVectorPainter(icon, hasSelectedMessage.not()),
                            contentDescription = stringResource(R.string.content_description_back_or_close),
                            modifier = Modifier.size(Dimension.Icon.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    val show = selectedMessage != null

                    val scale = animateFloatAsState(if (show) 1f else 0f, label = stringResource(R.string.animation_label_scale))

                    IconButton(onClick = {
                        context.copyToClipboard(selectedMessage.orEmpty(),
                            context.getString(R.string.message_list_alert_content_copy))
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_content_copy),
                            contentDescription = stringResource(R.string.content_description_copy),
                            modifier = Modifier
                                .size(Dimension.Icon.dp)
                                .scale(scale.value),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    IconButton(onClick = {
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_undo),
                            contentDescription = stringResource(R.string.content_description_undo),
                            modifier = Modifier
                                .size(Dimension.Icon.dp)
                                .scale(scale.value),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    IconButton(onClick = {
                        navController.navigate(ChatRoute.MessageDetail.name)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_translate),
                            contentDescription = stringResource(R.string.content_description_translate),
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
                scrollBehavior = scrollBehavior,
            )
        },
        content = {
            if (mockedSentMessages.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
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
                return@Scaffold
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.size(Dimension.Spacing.M.dp))
                mockedSentMessages.forEach { sentMessage ->
                    val isSelected = selectedMessage == sentMessage.id

                    SentMessageItem(
                        sentMessage,
                        isSelected,
                        onClick = { selectedMessage = null },
                        onLongPress = { id -> selectedMessage = id }
                    )

                    Spacer(modifier = Modifier.size(Dimension.Spacing.S.dp))
                }
                Spacer(modifier = Modifier.size(Dimension.Spacing.XXS.dp))
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(Dimension.Spacing.M.dp)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(Dimension.CornerRadius.dp))
                        .clickable { openBottomSheet = true },
                )  {
                    Row(
                        modifier = Modifier
                            .padding(Dimension.Spacing.S.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_send),
                            contentDescription = stringResource(R.string.content_description_send),
                            modifier = Modifier.size(Dimension.Icon.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.size(Dimension.Spacing.XS.dp))
                        Text(
                            text = stringResource(R.string.message_list_label_option),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }

            }
        }
    )

    if (openBottomSheet) {
        val windowInsets = if (edgeToEdgeEnabled)
            WindowInsets(0) else BottomSheetDefaults.windowInsets

        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState,
            windowInsets = windowInsets,
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ) {
            Column(
                modifier = Modifier.padding(horizontal = Dimension.Spacing.L.dp),
                horizontalAlignment = Alignment.End
            ) {
                mockedMessageOptions.forEach {
                    MessageOptionItem(it)
                    Spacer(modifier = Modifier.size(Dimension.Spacing.S.dp))
                }
                Spacer(modifier = Modifier.size(Dimension.Spacing.S.dp))
                Button(
                    onClick = { openBottomSheet = false },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = stringResource(R.string.content_description_send),
                        modifier = Modifier.size(Dimension.Icon.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.size(Dimension.Spacing.XS.dp))
                    Text(
                        text = stringResource(R.string.message_list_button_send),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
                Spacer(modifier = Modifier.size(Dimension.Spacing.L.dp))
            }
        }
    }
}

@Composable
fun MessageOptionItem(content: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(Dimension.CornerRadius.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer),
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = Dimension.Spacing.M.dp, vertical = Dimension.Spacing.S.dp),
            text = content,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SentMessageItem(
    sentMessage: SentMessage,
    isSelected: Boolean,
    onClick: (String) -> Unit,
    onLongPress: (String) -> Unit = {}
) {
    val alignment = if (sentMessage.isUserSent) Alignment.CenterEnd else Alignment.CenterStart
    val columnAlignment = if (sentMessage.isUserSent) Alignment.End else Alignment.Start

    val containerColor = if (sentMessage.isUserSent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
    val contentColor = if (sentMessage.isUserSent) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer

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

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = sentMessage.timestamp

    val time = "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)} ${if (calendar.get(Calendar.AM_PM) == Calendar.AM) "AM" else "PM"}"

    val haptic = LocalHapticFeedback.current

    val selectionColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.16f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                indication = rememberRipple(color = selectionColor),
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    onClick(sentMessage.id)
                },
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onLongPress(sentMessage.id)
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
                    .widthIn(min = 80.dp)
                ,
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
                text = time,
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