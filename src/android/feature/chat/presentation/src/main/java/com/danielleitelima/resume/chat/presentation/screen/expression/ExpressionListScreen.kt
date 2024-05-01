package com.danielleitelima.resume.chat.presentation.screen.expression

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavBackStackEntry
import com.danielleitelima.resume.chat.domain.Example
import com.danielleitelima.resume.chat.domain.Expression
import com.danielleitelima.resume.chat.presentation.R
import com.danielleitelima.resume.foundation.presentation.foundation.LocalNavHostController
import com.danielleitelima.resume.foundation.presentation.foundation.Route
import com.danielleitelima.resume.foundation.presentation.foundation.Screen
import com.danielleitelima.resume.foundation.presentation.foundation.getKoinInstance
import com.danielleitelima.resume.foundation.presentation.foundation.rememberViewModel
import com.danielleitelima.resume.foundation.presentation.foundation.theme.Dimension
import com.danielleitelima.resume.foundation.presentation.route.chat.ExpressionList

object ExpressionListScreen : Screen {
    override val route: Route
        get() = ExpressionList

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(backStackEntry: NavBackStackEntry) {
        val viewModel: ExpressionListViewModel = rememberViewModel { getKoinInstance() }
        val state by viewModel.state.collectAsState()

        val messageId = ExpressionList.getMessageId(backStackEntry)

        LaunchedEffect(messageId) {
            if (messageId != null){
                viewModel.setEvent(ExpressionListContract.Event.LoadExpressions(messageId))
            }
        }

        val navController = LocalNavHostController.current
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(stringResource(R.string.expression_list_title))
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

                    state.expressions.forEach { expression ->
                        ExpressionItem(
                            expression = expression,
                        )
                        Spacer(modifier = Modifier.height(Dimension.Spacing.S.dp))
                    }
                    Spacer(modifier = Modifier.height(Dimension.Spacing.S.dp))
                }
            },
        )
    }
}


@Composable
fun ExpressionItem(
    modifier: Modifier = Modifier,
    expression: Expression
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimension.CornerRadius.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            .padding(Dimension.Spacing.M.dp)
    ) {
        Text(
            text = expression.content,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.size(Dimension.Spacing.XS.dp))
        Text(
            text = expression.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.size(Dimension.Spacing.S.dp))
        expression.examples.forEachIndexed { index, example ->
            ExampleItem(
                example = example,
                number = index + 1
            )
            if (index < expression.examples.size - 1) {
                Spacer(modifier = Modifier.size(Dimension.Spacing.XS.dp))
            }
        }
    }
}


@Composable
fun ExampleItem(
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    example: Example,
    number: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "$number.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = maxLines,
        )
        Spacer(modifier = Modifier.size(Dimension.Spacing.XXS.dp))
        Column {
            Text(
                text = example.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = maxLines,
            )
            Spacer(modifier = Modifier.size(Dimension.Spacing.XXS.dp))

            Text(
                text = example.translation,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = maxLines,
            )
        }
    }
}
