package com.danielleitelima.resume.foundation.presentation.route.chat

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.danielleitelima.resume.foundation.presentation.foundation.Route

object ArticleDetail: Route{
    private const val ARGUMENT_ARTICLE_ID = "articleId"

    override val uri: String
        get() = "chat_article_detail/{${ARGUMENT_ARTICLE_ID}}"

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(ARGUMENT_ARTICLE_ID) {
                type = NavType.StringType
                defaultValue = ""
            }
        )

    fun routeWithArguments(articleId: String) = uri.replace("{${ARGUMENT_ARTICLE_ID}}", articleId)

    fun getArticleId(backStackEntry: NavBackStackEntry): String? {
        val id = backStackEntry.arguments?.getString(ARGUMENT_ARTICLE_ID).orEmpty()
        return if (id.isNotEmpty() && id.contains(ARGUMENT_ARTICLE_ID).not()) id else null
    }
}