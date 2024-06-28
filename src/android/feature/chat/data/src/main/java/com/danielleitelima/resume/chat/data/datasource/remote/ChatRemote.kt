package com.danielleitelima.resume.chat.data.datasource.remote

interface ChatRemote {
    suspend fun getChats(): ChatsResponse
    suspend fun getInitialMessageOptions(chatId: String): InitialMessageOptionsResponse
    suspend fun getReplyOptions(messageId: String): MessageOptionsResponse
    suspend fun getFirstReplyOptionDetail(messageId: String): ReplyMessageDetailResponse
    suspend fun getMessageDetail(messageId: String): MessageDetailResponse
    suspend fun getTargetLanguages(): AvailableLanguagesResponse
    suspend fun getTranslationLanguages(): AvailableLanguagesResponse
    fun getImageUrl(resourceId: String): String
    suspend fun getWords(ids: List<String>): WordsResponse
}
