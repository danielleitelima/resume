package com.danielleitelima.resume.chat.data.repository

import com.danielleitelima.resume.chat.domain.Chat
import com.danielleitelima.resume.chat.domain.Example
import com.danielleitelima.resume.chat.domain.Expression
import com.danielleitelima.resume.chat.domain.Meaning
import com.danielleitelima.resume.chat.domain.MessageDetail
import com.danielleitelima.resume.chat.domain.MessageOption
import com.danielleitelima.resume.chat.domain.RelatedArticle
import com.danielleitelima.resume.chat.domain.Section
import com.danielleitelima.resume.chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class FakeChatRepository : ChatRepository {

    private val mockedPossibleArticles = listOf(
        RelatedArticle(
            id = "art1",
            title = "The usage of 'How are you'",
            description = "Explains when and how to use the phrase 'How are you' in daily English conversation.",
            date = "2022-10-10",
            readTime = "5 mins",
            content = "The phrase 'How are you' is commonly used as a polite greeting..."
        ),
        RelatedArticle(
            id = "art2",
            title = "Responses to 'How are you'",
            description = "This article covers different ways to respond to 'How are you?' in English.",
            date = "2022-11-01",
            readTime = "4 mins",
            content = "Responding to 'How are you?' can vary from simple replies like 'I'm good' to more elaborate..."
        ),
        RelatedArticle(
            id = "art3",
            title = "Using polite phrases in English",
            description = "Learn about the importance of polite expressions such as 'thanks for asking' in everyday conversation.",
            date = "2022-12-15",
            readTime = "3 mins",
            content = "Polite phrases like 'thanks for asking' add a level of respect and consideration..."
        )
    )

    private val mockedPossibleMessages = listOf(
        MessageDetail(
            id = "msg1",
            timestamp = System.currentTimeMillis(),
            isUserSent = true,
            content = "Hello! How are you today?",
            translation = "Olá! Como você está hoje?",
            sections = listOf(
                Section(
                    id = "sec1",
                    position = 0,
                    content = "How are you",
                    meaning = Meaning(
                        id = "mean1",
                        content = "Como você está",
                        examples = listOf(
                            Example(
                                id = "ex1",
                                content = "How are you feeling today?",
                                translation = "Como você está se sentindo hoje?"
                            )
                        )
                    ),
                    otherMeanings = listOf(
                        Meaning(
                            id = "mean2",
                            content = "Como você está indo",
                            examples = listOf(
                                Example(
                                    id = "ex2",
                                    content = "How are you doing today?",
                                    translation = "Como você está indo hoje?"
                                )
                            )
                        ),
                        Meaning(
                            id = "mean3",
                            content = "Como você está passando",
                            examples = listOf(
                                Example(
                                    id = "ex3",
                                    content = "How are you getting along?",
                                    translation = "Como você está passando?"
                                )
                            )
                        )
                    )
                )
            ),
            expressions = listOf(
                Expression(
                    id = "exp1",
                    content = "How are you doing?",
                    description = "A more casual way to ask 'How are you?'",
                    examples = listOf(
                        Example(
                            id = "ex1",
                            content = "How are you doing today?",
                            translation = "Como você está hoje?"
                        ),
                        Example(
                            id = "ex2",
                            content = "How are you doing this week?",
                            translation = "Como você está esta semana?"
                        )
                    )
                )
            ),
            relatedArticles = mockedPossibleArticles,
            replyOptionsIds = listOf("msg2")
        ),
        MessageDetail(
            id = "msg2",
            timestamp = System.currentTimeMillis() + 5000,
            isUserSent = false,
            content = "I'm good, thanks! And you?",
            translation = "Estou bem, obrigado! E você?",
            sections = listOf(
                Section(
                    id = "sec2",
                    position = 0,
                    content = "And you",
                    meaning = Meaning(
                        id = "mean2",
                        content = "E você",
                        examples = listOf(
                            Example(
                                id = "ex2",
                                content = "I'm fine. And you?",
                                translation = "Estou bem. E você?"
                            )
                        )
                    ),
                    otherMeanings = listOf()
                )
            ),
            expressions = listOf(),
            relatedArticles = mockedPossibleArticles,
            replyOptionsIds = listOf("msg3")
        ),
        MessageDetail(
            id = "msg3",
            timestamp = System.currentTimeMillis() + 10000,
            isUserSent = true,
            content = "I'm also good, thanks for asking!",
            translation = "Eu também estou bem, obrigado por perguntar!",
            sections = listOf(
                Section(
                    id = "sec3",
                    position = 0,
                    content = "thanks for asking",
                    meaning = Meaning(
                        id = "mean3",
                        content = "obrigado por perguntar",
                        examples = listOf(
                            Example(
                                id = "ex3",
                                content = "I appreciate your asking.",
                                translation = "Agradeço por perguntar."
                            )
                        )
                    ),
                    otherMeanings = listOf()
                )
            ),
            expressions = listOf(),
            relatedArticles = listOf(
                RelatedArticle(
                    id = "art3",
                    title = "Using polite phrases in English",
                    description = "Learn about the importance of polite expressions such as 'thanks for asking' in everyday conversation.",
                    date = "2022-12-15",
                    readTime = "3 mins",
                    content = "Polite phrases like 'thanks for asking' add a level of respect and consideration..."
                )
            ),
            replyOptionsIds = listOf("msg2")
        )
    )

    private val mockedPossibleChats = mutableListOf(
        Chat(
            id = "1",
            title = "Lili",
            initialMessageOptions = listOf(
                MessageOption(
                    id = "msg1",
                    content = "Hello! How are you today?",
                )
            ),
            history = listOf()
        )
    )

    private val openChats = MutableStateFlow(mockedPossibleChats)

    override fun getById(chatId: String): Flow<Chat> {
        return openChats.map { chats ->
            chats.first { it.id == chatId }
        }
    }

    override fun getAll(sync: Boolean): Flow<List<Chat>> {
        return openChats
    }

    override suspend fun getMessageById(messageId: String): MessageDetail {
        return mockedPossibleMessages.first { it.id == messageId }
    }

    override suspend fun getRelatedArticleById(relatedArticleId: String): RelatedArticle {
        return mockedPossibleArticles.first { it.id == relatedArticleId }
    }

    override suspend fun selectMessageOption(
        chatId: String,
        messageOptionId: String,
    ){
        val currentlyOpenChats = openChats.first().toMutableList()

        var chat = currentlyOpenChats.first { it.id == chatId }

        var latestMessage = mockedPossibleMessages.first { it.id == messageOptionId }

        var updatedChat = chat.copy(
            history = chat.history + latestMessage
        )

        currentlyOpenChats[currentlyOpenChats.indexOf(chat)] = updatedChat
        chat = currentlyOpenChats.first { it.id == chatId }

        while (latestMessage.isUserSent) {
            latestMessage = mockedPossibleMessages.first { it.id == latestMessage.replyOptionsIds.first() }

            val currentHistory = chat.history.toMutableList()

            currentHistory.add(latestMessage)

            updatedChat = chat.copy(
                history = currentHistory
            )

            currentlyOpenChats[currentlyOpenChats.indexOf(chat)] = updatedChat
            chat = currentlyOpenChats.first { it.id == chatId }
        }

        val nextMessageId =
            if (latestMessage.replyOptionsIds.size == 1) {
                latestMessage.replyOptionsIds.first()
            } else null

        val nextMessage = nextMessageId?.let { messageId ->
            mockedPossibleMessages.first { it.id == messageId }
        }

        while (latestMessage.isUserSent.not() && nextMessage?.isUserSent?.not() == true) {
            latestMessage = nextMessage

            updatedChat = chat.copy(
                history = chat.history + latestMessage
            )

            currentlyOpenChats[currentlyOpenChats.indexOf(chat)] = updatedChat
            chat = currentlyOpenChats.first { it.id == chatId }
        }

        openChats.value = currentlyOpenChats
    }
}