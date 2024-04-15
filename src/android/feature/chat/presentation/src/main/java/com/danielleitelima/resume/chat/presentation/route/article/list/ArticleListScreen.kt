
package com.danielleitelima.resume.chat.presentation.route.article.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.chat.domain.RelatedArticle
import com.danielleitelima.resume.chat.presentation.R
import com.danielleitelima.resume.foundation.presentation.foundation.LocalNavHostController
import com.danielleitelima.resume.foundation.presentation.foundation.navigation.ChatRoute
import com.danielleitelima.resume.foundation.presentation.foundation.theme.Dimension
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListScreen() {
    val navController = LocalNavHostController.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    // TODO: Replace mocked data with real data
    val mockedRelatedArticle = RelatedArticle(
        id = UUID.randomUUID().toString(),
        title = "Lorem ipsum dolor sit amet consectetur Tincidunt.",
        description = "Lorem ipsum dolor sit amet consectetur. Sit blandit lectus etiam mattis. Curabitur imperdiet et egestas nulla. Eget scelerisque est etiam facilisi ultrices quis enim. Eget varius eget viverra tristique.",
        date = "2021-09-01",
        readTime = "5",
        content = ""
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.article_list_title))
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
                    .padding(horizontal = Dimension.Spacing.L.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.size(Dimension.Spacing.L.dp))

                for (i in 0..10) {
                    RelatedArticleItem(
                        relatedArticle = mockedRelatedArticle
                    ){
                        navController.navigate(ChatRoute.ArticleDetail.name)
                    }
                    Spacer(modifier = Modifier.height(Dimension.Spacing.S.dp))
                }
                Spacer(modifier = Modifier.height(Dimension.Spacing.S.dp))
            }
        },
    )
}

@Composable
fun RelatedArticleItem(
    modifier: Modifier = Modifier,
    relatedArticle: RelatedArticle,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimension.CornerRadius.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            .height(140.dp),
        shape = RoundedCornerShape(Dimension.CornerRadius.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimension.Elevation.dp,
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
