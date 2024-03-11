package com.danielleitelima.resume.data.datasource.local.entity

data class ActiveChatEntity(
    val id: String,
    val history: List<ActiveChatMessageEntity>,
)

data class ActiveChatMessageEntity(
    val id : String,
    val timestamp: Long,
)
