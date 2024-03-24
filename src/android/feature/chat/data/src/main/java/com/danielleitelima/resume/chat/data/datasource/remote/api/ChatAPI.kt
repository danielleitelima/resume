package com.danielleitelima.resume.chat.data.datasource.remote.api

import com.danielleitelima.resume.chat.data.datasource.remote.ChatDTO
import com.danielleitelima.resume.chat.data.datasource.remote.MessageDTO
import retrofit2.http.GET

interface ChatAPI {
    @GET("chat/main.json")
    suspend fun getAll(): List<ChatDTO>

    @GET("chat/{id}.json")
    suspend fun getMessagesById(id: String): List<MessageDTO>
}