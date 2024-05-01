package com.danielleitelima.resume.chat.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.danielleitelima.resume.chat.data.Database
import com.danielleitelima.resume.chat.data.datasource.remote.ArticleResponse
import com.danielleitelima.resume.chat.data.datasource.remote.AvailableLanguagesResponse
import com.danielleitelima.resume.chat.data.datasource.remote.ChatRemote
import com.danielleitelima.resume.chat.data.datasource.remote.ChatResponse
import com.danielleitelima.resume.chat.data.datasource.remote.ExampleResponse
import com.danielleitelima.resume.chat.data.datasource.remote.ExpressionResponse
import com.danielleitelima.resume.chat.data.datasource.remote.MeaningResponse
import com.danielleitelima.resume.chat.data.datasource.remote.MessageDetailDataResponse
import com.danielleitelima.resume.chat.data.datasource.remote.SectionResponse
import com.danielleitelima.resume.chat.domain.Article
import com.danielleitelima.resume.chat.domain.Chat
import com.danielleitelima.resume.chat.domain.Example
import com.danielleitelima.resume.chat.domain.Expression
import com.danielleitelima.resume.chat.domain.Language
import com.danielleitelima.resume.chat.domain.Meaning
import com.danielleitelima.resume.chat.domain.MessageDetail
import com.danielleitelima.resume.chat.domain.MessageOption
import com.danielleitelima.resume.chat.domain.OpenChat
import com.danielleitelima.resume.chat.domain.Section
import com.danielleitelima.resume.chat.domain.SentMessage
import com.danielleitelima.resume.chat.domain.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class IChatRepository(
    private val chatRemote: ChatRemote,
    private val database: Database,
) : ChatRepository {

    override fun getOpenChats(): Flow<List<OpenChat>> {
        val chatsFlow = database.chatQueries.getAllWithValidMessages().asFlow().mapToList(Dispatchers.IO)
        val targetLanguageFlow = database.targetLanguageQueries.getSelected().asFlow().mapToList(Dispatchers.IO)

        return combine(chatsFlow, targetLanguageFlow){
            chats, targetLanguage ->
            chats.map { chat ->
                val chatTranslationId = database.chatTranslationRelationQueries.getByChatId(chat.id).executeAsList().first { chatTranslationRelation ->
                    val translation = database.chatTranslationQueries.getById(chatTranslationRelation.chatTranslationId).executeAsOne()
                    translation.languagesCode == targetLanguage.first().code
                }.chatTranslationId

                val title = database.chatTranslationQueries.getById(chatTranslationId).executeAsList().first { chatTranslation ->
                    chatTranslation.languagesCode == targetLanguage.first().code
                }.title

                val history: List<SentMessage> = chat.messages.split(",").mapNotNull { messageId ->
                    val message = database.messageQueries.getById(messageId).executeAsOne()

                    val content = database.messageTranslationRelationQueries.getByMessageId(message.id).executeAsList().map { messageTranslationRelation ->
                        database.messageTranslationQueries.getById(messageTranslationRelation.messageTranslationId).executeAsOne()
                    }.first { messageTranslation ->
                        messageTranslation.languagesCode == targetLanguage.first().code
                    }.content

                    if (message.timestamp == null)
                        null
                    else
                        SentMessage(
                            id = message.id,
                            content = content,
                            timestamp = message.timestamp,
                            isUserSent = message.userSent ?: false,
                        )
                }

                OpenChat(
                    id = chat.id,
                    title = title,
                    history = history,
                )
            }
        }
    }

    override fun getOpenChat(chatId: String): Flow<OpenChat> {
        return database.chatQueries.getByIdWithMessages(chatId).asFlow().mapToList(Dispatchers.IO).map {
            val chat = it.first()

            val chatTranslationId = database.chatTranslationRelationQueries.getByChatId(chat.id).executeAsList().first { chatTranslationRelation ->
                val translation = database.chatTranslationQueries.getById(chatTranslationRelation.chatTranslationId).executeAsOne()
                translation.languagesCode == getSelectedTargetLanguage().code
            }.chatTranslationId

            val chatTranslation = database.chatTranslationQueries.getById(chatTranslationId).executeAsOne()

            val title = chatTranslation.title

            val messages = if(chat.messages != null) {
                chat.messages.split(",")
            } else{
                emptyList()
            }

            val history = messages.mapNotNull { messageId ->
                val message = database.messageQueries.getById(messageId).executeAsOne()

                val content = database.messageTranslationRelationQueries.getByMessageId(message.id).executeAsList().map { messageTranslationRelation ->
                    database.messageTranslationQueries.getById(messageTranslationRelation.messageTranslationId).executeAsOne()
                }.first { messageTranslation ->
                    messageTranslation.languagesCode == getSelectedTargetLanguage().code
                }.content

                if (message.timestamp == null)
                    null
                else
                    SentMessage(
                        id = message.id,
                        content = content,
                        timestamp = message.timestamp,
                        isUserSent = message.userSent ?: false,
                    )
            }

            OpenChat(
                id = chat.id,
                title = title,
                history = history,
            )
        }
    }

    override suspend fun addMessage(chatId: String, messageId: String) {
        database.messageQueries.updateTimestamp(
            timestamp = Calendar.getInstance().timeInMillis,
            id = messageId
        )
        database.chatMessageRelationQueries.insert(
            chatId = chatId,
            messageId = messageId,
        )
    }


    override suspend fun deleteMessages(chatId: String, messageIds: List<String>) {
        database.transaction {
            database.chatMessageRelationQueries.deleteByChatAndMessageIds(chatId, messageIds)
        }
    }

    override suspend fun getMessageDetail(messageId: String): MessageDetail {
        val message = database.messageQueries.getById(messageId).executeAsOne()

        val messageTranslationLanguage = database.messageTranslationRelationQueries.getByMessageId(messageId).executeAsList().map { messageTranslationRelation ->
            database.messageTranslationQueries.getById(messageTranslationRelation.messageTranslationId).executeAsOne()
        }.first {
            it.languagesCode == getSelectedTranslationLanguage().code
        }

        val messageTargetLanguage = database.messageTranslationRelationQueries.getByMessageId(messageId).executeAsList().map { messageTranslationRelation ->
            database.messageTranslationQueries.getById(messageTranslationRelation.messageTranslationId).executeAsOne()
        }.first {
            it.languagesCode == getSelectedTargetLanguage().code
        }

        val expressions = database.messageTranslationExpressionRelationQueries.getByMessageTranslationId(messageTargetLanguage.id).executeAsList().map {
            getExpression(it.expressionId)
        }

        val articles = database.messageTranslationArticleRelationQueries.getByMessageTranslationId(messageTranslationLanguage.id).executeAsList().map {
            getArticle(it.articleId)
        }

        val sections = database.messageTranslationSectionRelationQueries.getByMessageTranslationId(messageTargetLanguage.id).executeAsList().map {
            getSection(it.sectionId)
        }

        return MessageDetail(
            id = message.id,
            content = messageTargetLanguage.content,
            articles = articles,
            expressions = expressions,
            sections = sections,
            timestamp = message.timestamp,
            isUserSent = message.userSent ?: false,
            translation = messageTranslationLanguage.content,
        )
    }

    private fun getSection(sectionId: String): Section {
        val section = database.sectionQueries.getById(sectionId).executeAsOne()

        val sectionMeanings = database.sectionMeaningRelationQueries.getBySectionId(section.id).executeAsList().map {
            getMeaning(it.meaningId)
        }

        return Section(
            id = section.id,
            content = section.content,
            position = section.position.toInt(),
            meanings = sectionMeanings,
        )
    }

    private fun getMeaning(meaningId: String): Meaning {
        val meaningData = database.meaningQueries.getMeaningWithTranslation(meaningId).executeAsOneOrNull()
            ?: throw Exception("Meaning not found or translation unavailable for the selected languages")

        val examples = database.meaningExampleRelationQueries.getByMeaningId(meaningId).executeAsList().map {
            getExample(it.exampleId)
        }

        return Meaning(
            id = meaningData.id,
            content = meaningData.targetContent,
            translation = meaningData.translationContent,
            main = meaningData.main ?: false,
            examples = examples
        )
    }


    override suspend fun getArticle(articleId: String): Article {
        val articleWithTranslation = database.articleQueries.getArticleWithTranslation(articleId).executeAsOneOrNull()
            ?: throw Exception("Article not found or translation not available for the selected language")

        return Article(
            id = articleWithTranslation.id,
            dateCreated = articleWithTranslation.dateCreated,
            dateUpdated = articleWithTranslation.dateUpdated,
            readTime = articleWithTranslation.readTime.toInt(),
            title = articleWithTranslation.title,
            teaser = articleWithTranslation.teaser,
            content = articleWithTranslation.content
        )
    }

    private fun getExpression(expressionId: String): Expression {
        val expressionData = database.expressionQueries.getExpressionWithTranslation(expressionId).executeAsOneOrNull()
            ?: throw Exception("Expression not found or translation unavailable for the selected language")

        val examples = database.expressionExampleRelationQueries.getByExpressionId(expressionId).executeAsList().map { expressionExampleRelation ->
            getExample(expressionExampleRelation.exampleId)
        }

        return Expression(
            id = expressionData.id,
            content = expressionData.content,
            description = expressionData.description,
            examples = examples
        )
    }

    private fun getExample(exampleId: String): Example {
        // TODO: Fix this workaround. Only one item should be returned from this query.
        val exampleData = database.exampleQueries.getExampleWithTranslations(exampleId).executeAsList().firstOrNull()
            ?: throw Exception("No example found for the given ID or missing translations")

        return Example(
            id = exampleId,
            content = exampleData.targetContent,
            translation = exampleData.translationContent
        )
    }

    private fun MessageDetailDataResponse.cache() {
        database.messageQueries.insert(
            id = id,
            timestamp = null,
            userSent = userSent,
        )

        translations.forEach { messageTranslation ->
            database.messageTranslationQueries.insert(
                id = messageTranslation.id,
                content = messageTranslation.content,
                languagesCode = messageTranslation.languagesCode,
            )

            database.messageTranslationRelationQueries.insert(
                messageId = id,
                messageTranslationId = messageTranslation.id,
            )

            messageTranslation.articles.forEach { articleResponse ->
                val article = articleResponse.articleId

                database.articleQueries.insert(
                    id = article.id,
                    dateCreated = article.dateCreated.getTimestamp(),
                    dateUpdated = article.dateUpdated?.getTimestamp(),
                    readTime = article.readTime.toLong(),
                )

                article.translations.forEach { articleTranslation ->
                    database.articleTranslationQueries.insert(
                        id = articleTranslation.id,
                        title = articleTranslation.title,
                        teaser = articleTranslation.teaser,
                        content = articleTranslation.content,
                        languagesCode = articleTranslation.languagesCode,
                    )

                    database.articleTranslationRelationQueries.insert(
                        articleId = article.id,
                        articleTranslationId = articleTranslation.id,
                    )

                    database.messageTranslationArticleRelationQueries.insert(
                        messageTranslationId = messageTranslation.id,
                        articleId = article.id,
                    )
                }
            }

            messageTranslation.expressions.forEach { expressionResponse ->
                val expression = expressionResponse.expressionId

                database.expressionQueries.insert(
                    id = expression.id,
                    content = expression.content,
                    languagesCode = expression.languagesCode,
                )

                expression.translations.forEach { expressionTranslation ->
                    database.expressionTranslationQueries.insert(
                        id = expressionTranslation.id,
                        description = expressionTranslation.description,
                        languagesCode = expressionTranslation.languagesCode,
                    )

                    database.expressionTranslationRelationQueries.insert(
                        expressionId = expression.id,
                        expressionTranslationId = expressionTranslation.id,
                    )
                }

                expression.examples.forEach { exampleResponse ->
                    val example = exampleResponse.exampleId

                    database.exampleQueries.insert(
                        id = example.id
                    )

                    example.translations.forEach { exampleTranslation ->
                        database.exampleTranslationQueries.insert(
                            id = exampleTranslation.id,
                            content = exampleTranslation.content,
                            languagesCode = exampleTranslation.languagesCode,
                        )

                        database.exampleTranslationRelationQueries.insert(
                            exampleId = example.id,
                            exampleTranslationId = exampleTranslation.id,
                        )
                    }

                    database.expressionExampleRelationQueries.insert(
                        expressionId = expression.id,
                        exampleId = example.id,
                    )
                }

                database.messageTranslationExpressionRelationQueries.insert(
                    messageTranslationId = messageTranslation.id,
                    expressionId = expression.id,
                )
            }

            messageTranslation.sections.forEach { sectionResponse ->
                val section = sectionResponse.sectionId

                database.sectionQueries.insert(
                    id = section.id,
                    content = section.content,
                    position = section.position.toLong(),
                )

                section.meanings.forEach { meaningResponse ->
                    val meaning = meaningResponse.meaningId

                    database.meaningQueries.insert(
                        id = meaning.id,
                        main = meaning.main,
                    )

                    meaning.translations.forEach { meaningTranslation ->
                        database.meaningTranslationQueries.insert(
                            id = meaningTranslation.id,
                            content = meaningTranslation.content,
                            languagesCode = meaningTranslation.languagesCode,
                        )

                        database.meaningTranslationRelationQueries.insert(
                            meaningId = meaning.id,
                            meaningTranslationId = meaningTranslation.id,
                        )
                    }

                    database.sectionMeaningRelationQueries.insert(
                        sectionId = section.id,
                        meaningId = meaning.id,
                    )

                    database.messageTranslationSectionRelationQueries.insert(
                        messageTranslationId = messageTranslation.id,
                        sectionId = section.id,
                    )
                }
            }
        }
    }

    override suspend fun fetchChats(): List<Chat> {
        val response = chatRemote.getChats().data

        response.cache()

        return response.map {
            Chat(
                id = it.id,
                title = it.translations.first { it.languagesCode == getSelectedTargetLanguage().code }.title,
            )
        }
    }

    private fun List<ChatResponse>.cache() {
        this.forEach { chatResponse ->
            database.chatQueries.insert(
                id = chatResponse.id,
            )

            chatResponse.translations.forEach { chatTranslation ->
                database.chatTranslationQueries.insert(
                    id = chatTranslation.id,
                    title = chatTranslation.title,
                    languagesCode = chatTranslation.languagesCode,
                )

                database.chatTranslationRelationQueries.insert(
                    chatId = chatResponse.id,
                    chatTranslationId = chatTranslation.id,
                )
            }
        }
    }

    override suspend fun fetchInitialMessageOptions(chatId: String): List<MessageOption> {
        return chatRemote.getInitialMessageOptions(chatId).data.map {
            MessageOption(
                id = it.messageId.id,
                isUserSent = it.messageId.userSent,
                content = it.messageId.translations.first { translation ->
                    translation.languagesCode == getSelectedTargetLanguage().code
                }.content,
            )
        }
    }

    override suspend fun fetchReplyOptions(messageId: String): List<MessageOption> {
        return chatRemote.getReplyOptions(messageId).data.map {
            MessageOption(
                id = it.messageId.id,
                isUserSent = it.messageId.userSent,
                content = it.messageId.translations.first { translation ->
                    translation.languagesCode == getSelectedTargetLanguage().code
                }.content,
            )
        }
    }

    override suspend fun fetchMessageDetail(messageId: String) {
        val message = chatRemote.getMessageDetail(messageId).data.first()

        message.cache()
    }

    override suspend fun fetchFirstReplyDetail(messageId: String): MessageDetail {
        val message = chatRemote.getFirstReplyOptionDetail(messageId).data.first().relatedMessageId

        message.cache()

        val messageTargetLanguage = message.translations.first { translation ->
            translation.languagesCode == getSelectedTargetLanguage().code
        }

        val messageTranslationLanguage = message.translations.first { translation ->
            translation.languagesCode == getSelectedTranslationLanguage().code
        }

        val articles = messageTargetLanguage.articles.map { article ->
            article.toArticle(getSelectedTranslationLanguage().code)
        }

        val sections = messageTargetLanguage.sections.map { section ->
            section.toSection(getSelectedTargetLanguage().code, getSelectedTranslationLanguage().code)
        }

        val expressions = messageTargetLanguage.expressions.map { expression ->
            expression.toExpression(getSelectedTargetLanguage().code, getSelectedTranslationLanguage().code)
        }

        return MessageDetail(
            id = message.id,
            isUserSent = message.userSent,
            timestamp = Calendar.getInstance().timeInMillis,
            content = messageTargetLanguage.content,
            articles = articles,
            sections = sections,
            expressions = expressions,
            translation = messageTranslationLanguage.content,
        )
    }

    private fun ExampleResponse.toExample(targetLanguage: String, translationLanguage: String): Example {
        val content = exampleId.translations.first { translation ->
            translation.languagesCode == targetLanguage
        }.content

        val translation = exampleId.translations.first { translation ->
            translation.languagesCode == translationLanguage
        }.content

        return Example(
            id = exampleId.id,
            content = content,
            translation = translation
        )
    }

    private fun MeaningResponse.toMeaning(
        targetLanguage: String,
        translationLanguage: String
    ): Meaning {
        val content = meaningId.translations.first { translation ->
            translation.languagesCode == targetLanguage
        }.content

        val translation = meaningId.translations.first { translation ->
            translation.languagesCode == translationLanguage
        }.content

        val examples = meaningId.examples.map { example ->
            example.toExample(
                targetLanguage = targetLanguage,
                translationLanguage = translationLanguage
            )
        }

        return Meaning(
            id = meaningId.id,
            content = content,
            main = meaningId.main,
            translation = translation,
            examples = examples
        )
    }

    private fun SectionResponse.toSection(
        targetLanguage: String,
        userLanguage: String
    ): Section {
        val sectionMeanings = sectionId.meanings.map { meaning ->
            meaning.toMeaning(targetLanguage, userLanguage)
        }

        return Section(
            id = sectionId.id,
            meanings = sectionMeanings,
            content = sectionId.content,
            position = sectionId.position,
        )
    }

    private fun ArticleResponse.toArticle(userLanguage: String): Article {
        val articleTranslation = articleId.translations.first { translation ->
            translation.languagesCode == userLanguage
        }

        return Article(
            id = articleId.id,
            dateCreated = articleId.dateCreated.getTimestamp(),
            dateUpdated = articleId.dateUpdated?.getTimestamp(),
            readTime = articleId.readTime,
            title = articleTranslation.title,
            teaser = articleTranslation.teaser,
            content = articleTranslation.content,
        )
    }

    private fun ExpressionResponse.toExpression(targetLanguage: String, translationLanguage: String): Expression {
        val description = expressionId.translations.first { translation ->
            translation.languagesCode == translationLanguage
        }.description

        val examples = expressionId.examples.map { example ->
            example.toExample(
                targetLanguage = targetLanguage,
                translationLanguage = translationLanguage
            )
        }

        return Expression(
            id = expressionId.id,
            description = description,
            content = expressionId.content,
            examples = examples,
        )
    }

    private fun String.getTimestamp(): Long {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
        val zonedDateTime = ZonedDateTime.parse(this, formatter)
        return zonedDateTime.toInstant().toEpochMilli()
    }

    override suspend fun fetchTargetLanguages(): List<Language> {
        val response = chatRemote.getTargetLanguages()

        response.cacheTarget()

        return response.data.map { languageResponse ->
            Language(
                code = languageResponse.code,
                name = languageResponse.translations.first { translation ->
                    translation.languagesCode == getSelectedTranslationLanguage().code
                }.name,
                flagUrl = chatRemote.getImageUrl(languageResponse.flag.id),
                selected = languageResponse.default
            )
        }
    }

    private fun AvailableLanguagesResponse.cacheTranslation() {
        val currentLanguage = database.translationLanguageQueries.getSelected().executeAsOneOrNull()

        database.translationLanguageQueries.deleteAll()
        database.translationLanguageTranslationQueries.deleteAll()
        database.translationLanguageTranslationRelationQueries.deleteAll()

        this.data.forEach { languageResponse ->
            val selected = if (currentLanguage != null) {
                currentLanguage.code == languageResponse.code
            } else {
                languageResponse.default
            }

            database.translationLanguageQueries.insert(
                code = languageResponse.code,
                flagUrl = chatRemote.getImageUrl(languageResponse.flag.id),
                selected = selected,
                id = languageResponse.id,
            )

            languageResponse.translations.forEach { languageTranslation ->
                database.translationLanguageTranslationQueries.insert(
                    id = languageTranslation.id.toLong(),
                    name = languageTranslation.name,
                    languagesCode = languageTranslation.languagesCode
                )

                database.translationLanguageTranslationRelationQueries.insert(
                    translationLanguageId = languageResponse.id,
                    translationLanguageTranslationId = languageTranslation.id.toLong(),
                )
            }
        }
    }

    private fun AvailableLanguagesResponse.cacheTarget() {
        val currentLanguage = database.targetLanguageQueries.getSelected().executeAsOneOrNull()

        database.targetLanguageQueries.deleteAll()
        database.targetLanguageTranslationQueries.deleteAll()
        database.targetLanguageTranslationRelationQueries.deleteAll()

        this.data.forEach { languageResponse ->
            val selected = if (currentLanguage != null) {
                currentLanguage.code == languageResponse.code
            } else {
                languageResponse.default
            }

            database.targetLanguageQueries.insert(
                code = languageResponse.code,
                flagUrl = chatRemote.getImageUrl(languageResponse.flag.id),
                selected = selected,
                id = languageResponse.id,
            )

            languageResponse.translations.forEach { languageTranslation ->
                database.targetLanguageTranslationQueries.insert(
                    id = languageTranslation.id.toLong(),
                    name = languageTranslation.name,
                    languagesCode = languageTranslation.languagesCode
                )

                database.targetLanguageTranslationRelationQueries.insert(
                    targetLanguageId = languageResponse.id,
                    targetLanguageTranslationId = languageTranslation.id.toLong(),
                )
            }
        }
    }

    override suspend fun fetchTranslationLanguages(): List<Language> {
        val response = chatRemote.getTranslationLanguages()

        response.cacheTranslation()

        return response.data.map { languageResponse ->
            Language(
                code = languageResponse.code,
                name = languageResponse.translations.first { translation ->
                    translation.languagesCode == languageResponse.code
                }.name,
                flagUrl = chatRemote.getImageUrl(languageResponse.flag.id),
                selected = languageResponse.default
            )
        }
    }

    override fun getSelectedTargetLanguage(): Language {
        val language = database.targetLanguageQueries.getSelectedTargetLanguage().executeAsOneOrNull()
            ?: throw Exception("Target language not found")

        return Language(
            code = language.code,
            name = language.name,
            flagUrl = language.flagUrl,
            selected = language.selected ?: false
        )
    }

    override fun getSelectedTranslationLanguage(): Language {
        val language = database.translationLanguageQueries.getSelectedTranslationLanguage().executeAsOneOrNull()
            ?: throw Exception("Translation language not found")

        return Language(
            code = language.code,
            name = language.name,
            flagUrl = language.flagUrl,
            selected = language.selected ?: false
        )
    }

    override fun getTargetLanguages(): Flow<List<Language>> {
        return database.targetLanguageQueries.getCompleteTargetLanguages()
            .asFlow()
            .mapToList(Dispatchers.Main)
            .map { languages ->
                languages.map { language ->
                    Language(
                        code = language.code,
                        name = language.name.orEmpty(),
                        flagUrl = language.flagUrl,
                        selected = language.selected ?: false
                    )
                }
            }
    }

    override fun getTranslationLanguages(): Flow<List<Language>> {
        return database.translationLanguageQueries.getCompleteTranslationLanguages()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { languages ->
                languages.map { language ->
                    Language(
                        code = language.code,
                        name = language.name.orEmpty(),
                        flagUrl = language.flagUrl,
                        selected = language.selected ?: false
                    )
                }
            }
    }

    override fun setSelectedTargetLanguage(languageId: String) {
        database.targetLanguageQueries.setSelected(languageId)
    }

    override fun setSelectedTranslationLanguage(languageId: String) {
        database.translationLanguageQueries.setSelected(languageId)
    }
}

