package com.danielleitelima.resume.foundation.presentation.route.chat

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.danielleitelima.resume.foundation.presentation.foundation.Route

object MessageList: Route{
    private const val ARGUMENT_CHAT_ID = "chatId"

    override val uri: String
        get() = "chat_message_list/{${ARGUMENT_CHAT_ID}}"

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(ARGUMENT_CHAT_ID) {
                type = NavType.StringType
                defaultValue = ""
            }
        )

    fun routeWithArguments(chatId: String) = uri.replace("{${ARGUMENT_CHAT_ID}}", chatId)

    fun getChatId(backStackEntry: NavBackStackEntry): String? {
        val chatId = backStackEntry.arguments?.getString(ARGUMENT_CHAT_ID).orEmpty()
        return if (chatId.isNotEmpty() && chatId.contains(ARGUMENT_CHAT_ID).not()) chatId else null
    }
}