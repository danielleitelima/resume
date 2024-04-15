package com.danielleitelima.resume.foundation.presentation.foundation.navigation

import androidx.navigation.NavGraphBuilder

sealed class Route(open val name: String)

data object HomeRoute : Route("home")

sealed class ChatRoute(name: String) : Route(name) {
    data object Home : ChatRoute("chat_home")
    data object Creation : ChatRoute("chat_creation")
    data object MessageList : ChatRoute("chat_message_list")
    data object MessageDetail : ChatRoute("chat_message_detail")
    data object ExpressionList : ChatRoute("chat_expression_list")
    data object ArticleList : ChatRoute("chat_article_list")
    data object ArticleDetail : ChatRoute("chat_article_detail")

    companion object {
        fun register(){
            fun NavGraphBuilder.register(route: BaseRoute) {
                route.registerTo(this)
            }
        }
    }
}