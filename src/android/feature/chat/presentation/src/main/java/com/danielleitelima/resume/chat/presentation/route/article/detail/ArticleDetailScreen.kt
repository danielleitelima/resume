
package com.danielleitelima.resume.chat.presentation.route.article.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.danielleitelima.resume.chat.domain.RelatedArticle
import com.danielleitelima.resume.chat.presentation.R
import com.danielleitelima.resume.foundation.presentation.foundation.LocalNavHostController
import com.danielleitelima.resume.foundation.presentation.foundation.theme.Dimension
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen() {
    val navController = LocalNavHostController.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val mockedRelatedArticle = RelatedArticle(
        id = UUID.randomUUID().toString(),
        title = "Lorem ipsum dolor sit amet consectetur Tincidunt.",
        description = "Lorem ipsum dolor sit amet consectetur. Sit blandit lectus etiam mattis. Curabitur imperdiet et egestas nulla. Eget scelerisque est etiam facilisi ultrices quis enim. Eget varius eget viverra tristique.",
        date = "2021-09-01",
        readTime = "5",
        content = "Lorem ipsum dolor sit amet consectetur. Quam elementum malesuada quis quis. A vel non vitae mattis. Nullam id turpis elit pellentesque accumsan id eget etiam. Dictum nunc diam feugiat massa habitasse adipiscing. Enim viverra mauris semper ridiculus pellentesque ac nisi. Volutpat augue condimentum congue lorem. Venenatis ut varius ornare sit lacus eu varius feugiat molestie. In vitae in amet malesuada sagittis. Sociis at arcu mattis metus ultrices. Convallis convallis quis diam dui amet. Sed velit nisi tellus nec. Facilisis ullamcorper eget vitae tellus. Consectetur diam sit in in ac egestas mi lectus. Ipsum elit maecenas aliquet gravida. Enim vestibulum integer faucibus risus eget laoreet amet tincidunt pulvinar. In vitae fringilla nisl fermentum nibh urna et. Convallis proin nec orci nec nascetur commodo et quisque ullamcorper. Varius quam neque velit sed eros posuere a. Vitae varius lectus nullam viverra mauris ullamcorper quis massa penatibus. Vitae tempus justo id dolor ac malesuada facilisis sed sit. Vestibulum sollicitudin nunc mi scelerisque id sit enim diam. Aliquet malesuada leo tellus tincidunt donec. Arcu pharetra ut quis in ultrices turpis eu lorem leo. Neque iaculis turpis tempor nisl laoreet lacus pharetra sapien id. Etiam maecenas sit penatibus placerat lacus eget. Quis semper lectus a posuere urna aliquet posuere viverra. Tempus orci velit urna ipsum vitae. Vulputate cursus vitae felis libero non. Fames viverra faucibus turpis sit habitant curabitur aenean."
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
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
            ) {
                Spacer(modifier = Modifier.size(Dimension.Spacing.L.dp))
                Text(
                    text = mockedRelatedArticle.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.size(Dimension.Spacing.XXS.dp))
                Text(
                    text = stringResource(
                        R.string.message_detail_read_time,
                        mockedRelatedArticle.date,
                        mockedRelatedArticle.readTime
                    ),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.size(Dimension.Spacing.L.dp))
                Text(
                    text = mockedRelatedArticle.description,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.size(Dimension.Spacing.L.dp))
                Text(
                    text = mockedRelatedArticle.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.size(Dimension.Spacing.L.dp))
            }
        },
    )
}