package com.danielleitelima.resume.data.datasource.remote.api

import com.danielleitelima.resume.data.datasource.remote.dto.ChatDTO
import retrofit2.http.GET

interface ChatAPI {
    @GET("chat/main.json")
    suspend fun getAll(): List<ChatDTO>

    @GET("chat/{id}.json")
    suspend fun getMessagesById(id: String): List<MessageDTO>
}