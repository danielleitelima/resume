package com.danielleitelima.resume.chat.presentation.screen.message.detail

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.danielleitelima.resume.chat.domain.Example
import com.danielleitelima.resume.chat.domain.Expression
import com.danielleitelima.resume.chat.domain.Meaning
import com.danielleitelima.resume.chat.domain.MessageDetail
import com.danielleitelima.resume.chat.domain.RelatedArticle
import com.danielleitelima.resume.chat.domain.Section
import com.danielleitelima.resume.chat.presentation.R
import com.danielleitelima.resume.foundation.presentation.foundation.LocalNavHostController
import com.danielleitelima.resume.foundation.presentation.foundation.Route
import com.danielleitelima.resume.foundation.presentation.foundation.Screen
import com.danielleitelima.resume.foundation.presentation.foundation.getKoinInstance
import com.danielleitelima.resume.foundation.presentation.foundation.navigate
import com.danielleitelima.resume.foundation.presentation.foundation.rememberViewModel
import com.danielleitelima.resume.foundation.presentation.foundation.theme.Dimension
import com.danielleitelima.resume.foundation.presentation.route.chat.ArticleDetail
import com.danielleitelima.resume.foundation.presentation.route.chat.ArticleList
import com.danielleitelima.resume.foundation.presentation.route.chat.ExpressionList
import me.saket.extendedspans.ExtendedSpans
import me.saket.extendedspans.SquigglyUnderlineSpanPainter
import me.saket.extendedspans.drawBehind
import me.saket.extendedspans.rememberSquigglyUnderlineAnimator
import java.util.UUID
import kotlin.time.Duration.Companion.seconds

object MessageDetailScreen : Screen {
    override val route: Route
        get() = com.danielleitelima.resume.foundation.presentation.route.chat.MessageDetail

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    override fun Content(backStackEntry: NavBackStackEntry) {
        val viewModel: MessageDetailViewModel = rememberViewModel { getKoinInstance() }
        val state by viewModel.state.collectAsState()

        val navController = LocalNavHostController.current

        var selectedSection by rememberSaveable { mutableStateOf<String?>(null) }
        val bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        // TODO: Use actual lists to define the pager states
        val expressionPagerState = rememberPagerState(pageCount = { 10 })
        val relatedArticlePagerState = rememberPagerState(pageCount = { 10 })

        // TODO: Replace with real data
        val mockedMessage = "Haha, that sounds like quite an adventure! Animals always bring unexpected moments. 😄"
        val sections = listOf(
            Section(
                id = "1",
                position = 0,
                content = "Haha",
                meaning = Meaning(
                    id = "1",
                    content = "Haha",
                    examples = listOf(),
                ),
                otherMeanings = listOf()
            ),
            Section(
                id = "2",
                position = 0,
                content = "adventure",
                meaning = Meaning(
                    id = "2",
                    content = "adventure",
                    examples = listOf(),
                ),
                otherMeanings = listOf()
            ),
            Section(
                id = "3",
                position = 0,
                content = "Animals",
                meaning = Meaning(
                    id = "3",
                    content = "Animals",
                    examples = listOf(),
                ),
                otherMeanings = listOf()
            ),
            Section(
                id = "4",
                position = 0,
                content = "unexpected",
                meaning = Meaning(
                    id = "4",
                    content = "unexpected",
                    examples = listOf(),
                ),
                otherMeanings = listOf()
            ),
            Section(
                id = "5",
                position = 0,
                content = "moments",
                meaning = Meaning(
                    id = "5",
                    content = "moments",
                    examples = listOf(),
                ),
                otherMeanings = listOf()
            )
        )

        val messageDetail = MessageDetail(
            id = "1",
            timestamp = 1713131223418L,
            isUserSent = true,
            content = mockedMessage,
            translation = mockedMessage,
            sections = sections,
            expressions = listOf(),
            relatedArticles = listOf(),
            replyOptionsIds = listOf()
        )

        val mockedMessageTranslation = "Haha, isso parece ser uma aventura e tanto! Animais sempre trazem momentos inesperados. 😄"

        val mockedExample1 = Example(
            id = UUID.randomUUID().toString(),
            content = "Lorem ipsum dolor sit amet consectetur. Lectus ut erat amet ac euismod. Lectus ut erat amet ac euismod",
            translation = "Lorem ipsum dolor sit amet consectetur. Lectus ut erat amet ac euismod arcu ultrices leo quis. Lectus ut erat amet ac euismod"
        )

        val mockedExpression1 = Expression(
            id = UUID.randomUUID().toString(),
            content = "Lorem ipsum dolor sit amet consectetur. Lectus ut erat amet ac euismod arcu. Lectus ut erat amet ac euismod",
            description = "Lorem ipsum dolor sit amet consectetur. Lectus ut erat amet ac euismod arcu. Lectus ut erat amet ac euismod",
            examples = listOf(
                mockedExample1,
                mockedExample1,
            )
        )

        val mockedRelatedArticle = RelatedArticle(
            id = UUID.randomUUID().toString(),
            title = "Lorem ipsum dolor sit amet consectetur.",
            description = "Lorem ipsum dolor sit amet consectetur.",
            date = "2021-09-01",
            readTime = "5",
            content = ""
        )

        val onRelatedArticleClick: () -> Unit = {
            navController.navigate(ArticleDetail)
        }

        val highlightSections = messageDetail.getHighlightedRanges()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(stringResource(R.string.message_detail_title))
                    },
                    actions = {
                        IconButton(onClick = {
                            // TODO: Implement Text-to-Speech on message content
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_volume_up),
                                contentDescription = stringResource(R.string.content_description_volume_up),
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
                    horizontalAlignment = Alignment.Start,
                ) {
                    Spacer(modifier = Modifier.size(Dimension.Spacing.L.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimension.Spacing.L.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ExtendedSpansText(
                            text = buildAnnotatedString {
                                append(mockedMessage)
                                highlightSections.forEach { highlight ->
                                    addStyle(
                                        style = SpanStyle(
                                            textDecoration = TextDecoration.Underline,
                                            color = if (highlight.sectionId == selectedSection) {
                                                MaterialTheme.colorScheme.primary
                                            } else {
                                                MaterialTheme.colorScheme.secondary
                                            }
                                        ),
                                        start = highlight.start,
                                        end = highlight.end
                                    )
                                }
                            },
                            onClick = {
                                val clickedEntry = highlightSections.find { section ->
                                    section.start <= it.toInt() && section.end >= it.toInt()
                                }

                                val clickedSection = sections.find { section ->
                                    section.id == clickedEntry?.sectionId
                                }

                                clickedSection?.let { section ->
                                    selectedSection = section.id
                                }
                            }
                        )
                        Spacer(modifier = Modifier.size(Dimension.Spacing.S.dp))
                        Text(
                            text = mockedMessageTranslation,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.size(Dimension.Spacing.XL.dp))
                    Text(
                        text = stringResource(R.string.message_detail_section_expression_label),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimension.Spacing.L.dp)
                    )
                    Spacer(modifier = Modifier.size(Dimension.Spacing.L.dp))

                    HorizontalPager(state = expressionPagerState,
                        contentPadding = PaddingValues(horizontal = Dimension.Spacing.L.dp),
                        pageSpacing = Dimension.Spacing.S.dp
                    ) {
                        ExpressionItem(
                            expression = mockedExpression1,
                        )
                    }

                    Spacer(modifier = Modifier.size(Dimension.Spacing.L.dp))
                    SeeMoreButton(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = Dimension.Spacing.L.dp),
                    ){
                        navController.navigate(ExpressionList)
                    }

                    Spacer(modifier = Modifier.size(Dimension.Spacing.M.dp))
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
                        state = relatedArticlePagerState,
                        contentPadding = PaddingValues(horizontal = Dimension.Spacing.L.dp),
                        pageSpacing = Dimension.Spacing.S.dp,
                    ) {
                        RelatedArticleItem(
                            relatedArticle = mockedRelatedArticle,
                            onClick = onRelatedArticleClick,
                        )
                    }

                    Spacer(modifier = Modifier.size(Dimension.Spacing.L.dp))
                    SeeMoreButton(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = Dimension.Spacing.L.dp),
                    ){
                        navController.navigate(ArticleList)
                    }
                }
            },
        )

        if (selectedSection != null) {
            ModalBottomSheet(
                onDismissRequest = { selectedSection = null },
                sheetState = bottomSheetState,
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            ) {
                Column(
                    modifier = Modifier
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
                            text = "Section content",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Spacer(modifier = Modifier.width(Dimension.Spacing.XS.dp))
                        IconButton(onClick = {}) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_volume_up),
                                contentDescription = stringResource(R.string.content_description_volume_up),
                                modifier = Modifier.size(Dimension.Spacing.L.dp),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(Dimension.Spacing.M.dp))

                    // TODO: Replace with real data
                    Text(
                        text = "Description of the meaning of the section.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Spacer(modifier = Modifier.height(Dimension.Spacing.XS.dp))
                    Text(
                        text = "Example of a phrase using the section.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(modifier = Modifier.height(Dimension.Spacing.M.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(Dimension.Spacing.M.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.message_detail_section_label_other_meaning),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(Dimension.Spacing.M.dp))
                    Text(
                        text = "Description of another meaning for the section.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Spacer(modifier = Modifier.height(Dimension.Spacing.XS.dp))
                    Text(
                        text = "Example of a phrase using the section.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(modifier = Modifier.height(Dimension.Spacing.M.dp))
                    Text(
                        text = "Description of another meaning for the section.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Spacer(modifier = Modifier.height(Dimension.Spacing.XS.dp))
                    Text(
                        text = "Example of a phrase using the section.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(modifier = Modifier.height(Dimension.Spacing.M.dp))
                    Text(
                        text = "Description of another meaning for the section.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Spacer(modifier = Modifier.height(Dimension.Spacing.XS.dp))
                    Text(
                        text = "Example of a phrase using the section.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(modifier = Modifier.height(Dimension.Spacing.XXL.dp))
                }
            }
        }
    }
}

@Composable
fun ExpressionItem(
    modifier: Modifier = Modifier,
    expression: Expression
) {
    val configuration = LocalConfiguration.current

    // TODO: Optimize this logic
    val width = configuration.screenWidthDp.dp - (48.dp)

    val exampleCountText = if (expression.examples.size == 1) {
        "${expression.examples.size} example"
    } else {
        "${expression.examples.size} examples"
    }

    Column(
        modifier = modifier
            .width(width)
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
            text = expression.content,
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
            text = exampleCountText,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.End,
        )
    }
}

@Composable
private fun RelatedArticleItem(
    modifier: Modifier = Modifier,
    relatedArticle: RelatedArticle,
    onClick: () -> Unit = {},
) {
    val configuration = LocalConfiguration.current

    // TODO: Optimize this logic
    val width = configuration.screenWidthDp.dp - (48.dp)

    Card(
        modifier = modifier
            .width(width)
            .height(140.dp),
        shape = RoundedCornerShape(Dimension.CornerRadius.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimension.Elevation.dp
        ),
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier.padding(Dimension.Spacing.M.dp),
        ) {
            Text(
                text = relatedArticle.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.size(Dimension.Spacing.XS.dp))
            Text(
                text = relatedArticle.description,
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
                text = stringResource(
                    R.string.message_detail_read_time,
                    relatedArticle.date,
                    relatedArticle.readTime
                ),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.End,
            )
        }
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
