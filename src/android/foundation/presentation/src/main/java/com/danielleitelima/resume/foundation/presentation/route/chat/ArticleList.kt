package com.danielleitelima.resume.foundation.presentation.route.chat

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.danielleitelima.resume.foundation.presentation.foundation.Route

object ArticleList: Route{
    private const val ARGUMENT_MESSAGE_ID = "messageId"

    override val uri: String
        get() = "chat_article_list/{${ARGUMENT_MESSAGE_ID}}"

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(ARGUMENT_MESSAGE_ID) {
                type = NavType.StringType
                defaultValue = ""
            }
        )

    fun routeWithArguments(messageId: String) = uri.replace("{${ARGUMENT_MESSAGE_ID}}", messageId)

    fun getMessageId(backStackEntry: NavBackStackEntry): String? {
        val id = backStackEntry.arguments?.getString(ARGUMENT_MESSAGE_ID).orEmpty()
        return if (id.isNotEmpty() && id.contains(ARGUMENT_MESSAGE_ID).not()) id else null
    }
}