package com.danielleitelima.resume.chat.presentation.screen.message.detail

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import com.danielleitelima.resume.chat.domain.Article
import com.danielleitelima.resume.chat.domain.Example
import com.danielleitelima.resume.chat.domain.Expression
import com.danielleitelima.resume.chat.domain.MessageDetail
import com.danielleitelima.resume.chat.domain.Vocabulary
import com.danielleitelima.resume.chat.presentation.R
import com.danielleitelima.resume.chat.presentation.screen.component.ArticleItem
import com.danielleitelima.resume.foundation.presentation.foundation.LocalNavHostController
import com.danielleitelima.resume.foundation.presentation.foundation.Route
import com.danielleitelima.resume.foundation.presentation.foundation.Screen
import com.danielleitelima.resume.foundation.presentation.foundation.TextToSpeechManager
import com.danielleitelima.resume.foundation.presentation.foundation.getKoinInstance
import com.danielleitelima.resume.foundation.presentation.foundation.rememberViewModel
import com.danielleitelima.resume.foundation.presentation.foundation.theme.Dimension
import com.danielleitelima.resume.foundation.presentation.route.chat.ArticleDetail
import com.danielleitelima.resume.foundation.presentation.route.chat.ArticleList
import com.danielleitelima.resume.foundation.presentation.route.chat.ExpressionList
import me.saket.extendedspans.ExtendedSpans
import me.saket.extendedspans.SquigglyUnderlineSpanPainter
import me.saket.extendedspans.drawBehind
import me.saket.extendedspans.rememberSquigglyUnderlineAnimator
import kotlin.time.Duration.Companion.seconds
import com.danielleitelima.resume.foundation.presentation.route.chat.MessageDetail as MessageDetailRoute

object MessageDetailScreen : Screen {
    override val route: Route
        get() = MessageDetailRoute

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(backStackEntry: NavBackStackEntry) {
        val navController = LocalNavHostController.current
        val messageId = MessageDetailRoute.getMessageId(backStackEntry)

        val viewModel: MessageDetailViewModel = rememberViewModel { getKoinInstance() }
        val state by viewModel.state.collectAsState()

        val textToSpeechManager: TextToSpeechManager = remember { getKoinInstance() }
        var selectedVocabulary by remember { mutableStateOf<String?>(null) }
        val bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        val isMessagePlaying = remember { mutableStateOf(false) }
        val isWordPlaying = remember { mutableStateOf(false) }

        val messageDetail = state.messageDetail

        LaunchedEffect(messageId) {
            if (messageId != null){
                viewModel.setEvent(MessageDetailContract.Event.LoadMessageDetail(messageId))
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(stringResource(R.string.message_detail_title))
                    },
                    actions = {
                        TextToSpeechButton(
                            text = messageDetail?.content.orEmpty(),
                            isPlaying = isMessagePlaying
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
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
                if (messageDetail == null) return@Scaffold

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Spacer(modifier = Modifier.size(Dimension.Spacing.L.dp))

                    ContentSection(
                        messageDetail = messageDetail,
                        onVocabularySelected = {
                            val clickedVocabulary = messageDetail.vocabularies.find { vocabulary ->
                                vocabulary.beginOffset <= it.toInt() && vocabulary.beginOffset + vocabulary.content.length >= it.toInt()
                            }

                            clickedVocabulary?.let { section ->
                                selectedVocabulary = section.id
                            }
                        }
                    )

                    Spacer(modifier = Modifier.size(Dimension.Spacing.XL.dp))

                    ExpressionSection(
                        expressions = messageDetail.expressions,
                        onSeeMoreClick = {
                            navController.navigate(ExpressionList.routeWithArguments(messageId.orEmpty()))
                        }
                    )

                    ArticleSection(
                        articles = messageDetail.articles,
                        onArticleClick = { articleId ->
                            navController.navigate(ArticleDetail.routeWithArguments(articleId))
                        },
                        onSeeMoreClick = {
                            navController.navigate(
                                ArticleList.routeWithArguments(
                                    messageId = messageId.orEmpty()
                                )
                            )
                        }
                    )
                }
            },
        )

        if (selectedVocabulary != null) {
            val vocabulary = messageDetail?.vocabularies?.find { it.id == selectedVocabulary } ?: return
            textToSpeechManager.stopSpeaking()
            isMessagePlaying.value = false

            VocabularyModal(bottomSheetState = bottomSheetState, vocabulary = vocabulary, isPlaying = isWordPlaying){
                selectedVocabulary = null
                textToSpeechManager.stopSpeaking()
                isWordPlaying.value = false
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VocabularyModal(
    modifier: Modifier = Modifier,
    bottomSheetState: SheetState,
    vocabulary: Vocabulary,
    isPlaying: MutableState<Boolean>,
    onDismiss: () -> Unit = {},
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = Dimension.Spacing.L.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = vocabulary.content,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.width(Dimension.Spacing.XS.dp))
                TextToSpeechButton(
                    text = vocabulary.content,
                    isPlaying = isPlaying
                )
            }
            Spacer(modifier = Modifier.height(Dimension.Spacing.M.dp))

//            val mainMeaning = vocabulary.meanings.firstOrNull { it.main }
//            MeaningItem(meaning = mainMeaning)
//            Spacer(modifier = Modifier.height(Dimension.Spacing.M.dp))
//
//            val otherMeanings = vocabulary.meanings.filter { it.main.not() }
//            if (otherMeanings.isEmpty()) {
//                return@Column
//            }
//
//            HorizontalDivider()
//            Spacer(modifier = Modifier.height(Dimension.Spacing.M.dp))
//            MeaningList(meanings = otherMeanings)
//            Spacer(modifier = Modifier.height(Dimension.Spacing.XXL.dp))
        }
    }
}

@Composable
private fun ContentSection(
    modifier: Modifier = Modifier,
    messageDetail: MessageDetail,
    onVocabularySelected: (String) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimension.Spacing.L.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Log.d("MessageDetailScreen", "ContentSection: messageDetail.content: ${messageDetail.content}")
        ExtendedSpansText(

            text = buildAnnotatedString {
                append(messageDetail.content)
                messageDetail.vocabularies.forEach { vocabulary ->
                    Log.d("MessageDetailScreen", "ContentSection: vocabulary: ${vocabulary.content}, beginOffset: ${vocabulary.beginOffset}, endOffset: ${vocabulary.beginOffset + vocabulary.content.length}")
                    addStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        start = vocabulary.beginOffset,
                        end = vocabulary.beginOffset + vocabulary.content.length
                    )
                }
            },
            onClick = onVocabularySelected
        )
        Spacer(modifier = Modifier.size(Dimension.Spacing.S.dp))
        Text(
            text = messageDetail.translation,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ExpressionSection(
    modifier: Modifier = Modifier,
    expressions: List<Expression>,
    onSeeMoreClick: () -> Unit = {},
) {
    val configuration = LocalConfiguration.current

    Column(
        modifier = modifier
    ) {
        if (expressions.isNotEmpty()){
            Text(
                text = stringResource(R.string.message_detail_section_expression_label),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimension.Spacing.L.dp)
            )
            Spacer(modifier = Modifier.size(Dimension.Spacing.L.dp))

            HorizontalPager(
                state = rememberPagerState(
                    pageCount = { expressions.size }
                ),
                contentPadding = PaddingValues(horizontal = Dimension.Spacing.L.dp),
                pageSpacing = Dimension.Spacing.S.dp
            ) {position ->
                val expression = expressions[position]
                val width = configuration.screenWidthDp.dp - (Dimension.Spacing.L.dp * 2)

                ExpressionItem(
                    modifier = Modifier.width(width),
                    expression = expression,
                )
            }

            Spacer(modifier = Modifier.size(Dimension.Spacing.L.dp))

            SeeMoreButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = Dimension.Spacing.L.dp),
                onClick = onSeeMoreClick
            )

            Spacer(modifier = Modifier.size(Dimension.Spacing.M.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ArticleSection(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onArticleClick: (String) -> Unit = {},
    onSeeMoreClick: () -> Unit = {},
) {
    val configuration = LocalConfiguration.current

    Column(
        modifier = modifier
    ) {
        if (articles.isNotEmpty()){
            Text(
                text = stringResource(R.string.message_detail_section_article_label),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimension.Spacing.L.dp)
            )
            Spacer(modifier = Modifier.size(Dimension.Spacing.L.dp))

            HorizontalPager(
                state = rememberPagerState(pageCount = { articles.size}),
                contentPadding = PaddingValues(horizontal = Dimension.Spacing.L.dp),
                pageSpacing = Dimension.Spacing.S.dp,
            ) {position ->
                val article = articles[position]
                val width = configuration.screenWidthDp.dp - (Dimension.Spacing.L.dp * 2)

                ArticleItem(
                    modifier = Modifier.width(width),
                    article = article,
                    onClick = onArticleClick,
                )
            }

            Spacer(modifier = Modifier.size(Dimension.Spacing.L.dp))
            SeeMoreButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = Dimension.Spacing.L.dp),
                onClick = onSeeMoreClick
            )
        }
    }
}

//@Composable
//private fun MeaningList(
//    modifier: Modifier = Modifier,
//    meanings: List<Meaning>
//){
//    Column(
//        modifier = modifier
//    ) {
//        Text(
//            modifier = Modifier.fillMaxWidth(),
//            text = stringResource(R.string.message_detail_section_label_other_meaning),
//            style = MaterialTheme.typography.labelSmall,
//            color = MaterialTheme.colorScheme.onSurface,
//            textAlign = TextAlign.Center,
//        )
//        Spacer(modifier = Modifier.height(Dimension.Spacing.S.dp))
//        meanings.forEach { meaning ->
//            MeaningItem(
//                meaning = meaning
//            )
//            Spacer(modifier = Modifier.height(Dimension.Spacing.S.dp))
//        }
//    }
//}
//
//@Composable
//private fun MeaningItem(
//    modifier: Modifier = Modifier,
//    meaning: Meaning?
//) {
//    if (meaning == null) return
//
//    Column(
//        modifier = modifier
//    ) {
//        Text(
//            text = meaning.translation,
//            style = MaterialTheme.typography.titleMedium,
//            color = MaterialTheme.colorScheme.onSurface,
//        )
//        Spacer(modifier = Modifier.height(Dimension.Spacing.XS.dp))
//        ExampleList(examples = meaning.examples)
//    }
//}

@Composable
private fun ExampleList(
    modifier: Modifier = Modifier,
    examples: List<Example>,
) {
    Column(
        modifier = modifier
    ) {
        examples.forEach{ example ->
            Text(
                text = example.content,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(Dimension.Spacing.XS.dp))
        }
    }
}

@Composable
private fun ExpressionItem(
    modifier: Modifier = Modifier,
    expression: Expression
) {
    val exampleCountRes = if (expression.examples.size == 1) {
        R.string.expression_example_count_single
    } else {
        R.string.expression_example_count_multiple
    }

    val exampleCount = stringResource(id = exampleCountRes, expression.examples.size)

    Column(
        modifier = modifier
            .height(140.dp)
            .clip(RoundedCornerShape(Dimension.CornerRadius.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            .padding(Dimension.Spacing.M.dp),
    ) {
        Text(
            text = expression.content,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.size(Dimension.Spacing.XS.dp))
        Text(
            text = expression.description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.weight(1.0f))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End),
            text = exampleCount,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.End,
        )
    }
}
@Composable
private fun ExtendedSpansText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {},
) {
    val underlineAnimator = rememberSquigglyUnderlineAnimator(duration = 5.seconds)
    val extendedSpans = remember {
        ExtendedSpans(
            SquigglyUnderlineSpanPainter(
                width = 2.sp,
                wavelength = 8.sp,
                amplitude = 1.sp,
                bottomOffset = 0.sp,
                animator = underlineAnimator
            )
        )
    }

    ClickableText(
        style = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            lineHeight = 30.sp
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
private fun SeeMoreButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(vertical = Dimension.Spacing.XS.dp)
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ),
    ){
        Text(
            text = stringResource(R.string.message_detail_button_see_more),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}
@Composable
private fun TextToSpeechButton(
    modifier: Modifier = Modifier,
    text: String,
    isPlaying: MutableState<Boolean>
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
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}
