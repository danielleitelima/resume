package com.danielleitelima.resume.chat.presentation.screen.article.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import com.danielleitelima.resume.chat.presentation.R
import com.danielleitelima.resume.chat.presentation.screen.component.ArticleItem
import com.danielleitelima.resume.foundation.presentation.foundation.LocalNavHostController
import com.danielleitelima.resume.foundation.presentation.foundation.Route
import com.danielleitelima.resume.foundation.presentation.foundation.Screen
import com.danielleitelima.resume.foundation.presentation.foundation.getKoinInstance
import com.danielleitelima.resume.foundation.presentation.foundation.rememberViewModel
import com.danielleitelima.resume.foundation.presentation.foundation.theme.Dimension
import com.danielleitelima.resume.foundation.presentation.route.chat.ArticleDetail
import com.danielleitelima.resume.foundation.presentation.route.chat.ArticleList
import com.danielleitelima.resume.foundation.presentation.route.chat.ExpressionList

object ArticleListScreen : Screen {
    override val route: Route
        get() = ArticleList

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(backStackEntry: NavBackStackEntry) {
        val viewModel: ArticleListViewModel = rememberViewModel { getKoinInstance() }
        val state by viewModel.state.collectAsState()

        val messageId = ExpressionList.getMessageId(backStackEntry)

        LaunchedEffect(messageId) {
            if (messageId != null){
                viewModel.setEvent(ArticleListContract.Event.LoadArticles(messageId))
            }
        }

        val navController = LocalNavHostController.current
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

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

                    state.articles.forEach { article ->
                        ArticleItem(
                            article = article
                        ){
                            navController.navigate(ArticleDetail.routeWithArguments(article.id))
                        }
                        Spacer(modifier = Modifier.height(Dimension.Spacing.S.dp))
                    }
                    Spacer(modifier = Modifier.height(Dimension.Spacing.S.dp))
                }
            },
        )
    }
}
