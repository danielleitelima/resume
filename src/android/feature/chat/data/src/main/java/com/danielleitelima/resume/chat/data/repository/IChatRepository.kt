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
import com.danielleitelima.resume.chat.data.datasource.remote.MessageDetailDataResponse
import com.danielleitelima.resume.chat.data.datasource.remote.VocabularyResponse
import com.danielleitelima.resume.chat.data.datasource.remote.WordsResponse
import com.danielleitelima.resume.chat.domain.Article
import com.danielleitelima.resume.chat.domain.Chat
import com.danielleitelima.resume.chat.domain.Example
import com.danielleitelima.resume.chat.domain.Expression
import com.danielleitelima.resume.chat.domain.Language
import com.danielleitelima.resume.chat.domain.Message
import com.danielleitelima.resume.chat.domain.MessageOption
import com.danielleitelima.resume.chat.domain.OpenChat
import com.danielleitelima.resume.chat.domain.SentMessage
import com.danielleitelima.resume.chat.domain.Vocabulary
import com.danielleitelima.resume.chat.domain.Word
import com.danielleitelima.resume.chat.domain.WordDefinition
import com.danielleitelima.resume.chat.domain.WordMeaning
import com.danielleitelima.resume.chat.domain.WordRelated
import com.danielleitelima.resume.chat.domain.WordSubDefinitions
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

                val messages = chat.messages.split(",")
                val chatMessages = chat.relationIds.split(",")
                val timestamps = chat.timestamps.split(",").mapNotNull { it.toLongOrNull() }

                val history = messages.zip(chatMessages.zip(timestamps)) { message, pair ->
                    val content = database.messageTranslationRelationQueries.getByMessageId(message).executeAsList().map { messageTranslationRelation ->
                        database.messageTranslationQueries.getById(messageTranslationRelation.messageTranslationId).executeAsOne()
                    }.first { messageTranslation ->
                        messageTranslation.languagesCode == getSelectedTargetLanguage().code
                    }.content

                    val isUserSent = database.messageQueries.getById(message).executeAsOne().userSent
                    SentMessage(
                        id = pair.first,
                        messageId = message,
                        content = content,
                        timestamp = pair.second,
                        isUserSent = isUserSent ?: false,
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
        return database.chatQueries.getByIdWithMessages(chatId).asFlow().map {
            val chat = it.executeAsOne()

            val chatTranslationId = database.chatTranslationRelationQueries.getByChatId(chat.id).executeAsList().first { chatTranslationRelation ->
                val translation = database.chatTranslationQueries.getById(chatTranslationRelation.chatTranslationId).executeAsOne()
                translation.languagesCode == getSelectedTargetLanguage().code
            }.chatTranslationId

            val chatTranslation = database.chatTranslationQueries.getById(chatTranslationId).executeAsOne()

            val title = chatTranslation.title

            val messages = chat.messages.split(",")
            val chatMessages = chat.relationIds.split(",")
            val timestamps = chat.timestamps.split(",").mapNotNull { timestamp -> timestamp.toLongOrNull() }

            val history = messages.zip(chatMessages.zip(timestamps)) { message, pair ->
                val content = database.messageTranslationRelationQueries.getByMessageId(message).executeAsList().map { messageTranslationRelation ->
                    database.messageTranslationQueries.getById(messageTranslationRelation.messageTranslationId).executeAsOne()
                }.first { messageTranslation ->
                    messageTranslation.languagesCode == getSelectedTargetLanguage().code
                }.content

                val isUserSent = database.messageQueries.getById(message).executeAsOne().userSent
                SentMessage(
                    id = pair.first,
                    messageId = message,
                    content = content,
                    timestamp = pair.second,
                    isUserSent = isUserSent ?: false,
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
        database.chatMessageRelationQueries.insert(
            chatId = chatId,
            messageId = messageId,
            timestamp = Calendar.getInstance().timeInMillis
        )
    }

    override suspend fun deleteMessages(chatId: String, messageIds: List<String>) {
        database.transaction {
            database.chatMessageRelationQueries.deleteByChatAndMessageIds(chatId, messageIds)
        }
    }

    override suspend fun getMessage(messageId: String): Message {
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

        val vocabularies = database.messageTranslationVocabularyRelationQueries.getByMessageTranslationId(messageTargetLanguage.id).executeAsList().map {
            getVocabulary(it.vocabularyId)
        }

        return Message(
            id = message.id,
            content = messageTargetLanguage.content,
            articles = articles,
            expressions = expressions,
            isUserSent = message.userSent ?: false,
            translation = messageTranslationLanguage.content,
            vocabularies = vocabularies,
        )
    }

    override suspend fun getVocabulary(vocabularyId: String): Vocabulary {
        val vocabulary = database.vocabularyQueries.getById(vocabularyId).executeAsOneOrNull()
            ?: throw Exception("Vocabulary not found or translation not available for the selected language")

        return Vocabulary(
            id = vocabulary.id,
            word = vocabulary.word,
            content = vocabulary.content,
            lemma = vocabulary.lemma,
            beginOffset = vocabulary.beginOffset.toInt(),
            partOfSpeech = vocabulary.partOfSpeech,
            aspect = vocabulary.aspect,
            case = vocabulary.case,
            form = vocabulary.form,
            tense = vocabulary.tense,
            voice = vocabulary.voice,
            mood = vocabulary.mood,
            number = vocabulary.number,
            person = vocabulary.person,
            gender = vocabulary.gender,
            dependency = vocabulary.dependency,
            dependencyType = vocabulary.dependencyType,
        )
    }

    override suspend fun getWord(wordId: String): Word {
        val word = try {
            database.wordQueries.getById(wordId).executeAsOne()
        } catch (e: Exception) {
            throw Exception("Word not found for the id $wordId")
        }

        val meanings = database.wordMeaningRelationQueries.getByWordId(wordId).executeAsList().map {
            getWordMeaning(it.wordMeaningId)
        }

        return Word(
            id = word.id,
            content = word.content.orEmpty(),
            meanings = meanings
        )
    }

    private fun getWordMeaning(wordMeaningId: String): WordMeaning {
        val wordMeaning = database.wordMeaningQueries.getById(wordMeaningId).executeAsOneOrNull()
            ?: throw Exception("Word meaning not found for the id $wordMeaningId")

        val definitions = database.wordMeaningDefinitionRelationQueries.getByWordMeaningId(wordMeaningId).executeAsList().map {
            getDefinition(it.wordDefinitionId)
        }

        return WordMeaning(
            id = wordMeaning.id,
            partsOfSpeech = wordMeaning.partOfSpeech.orEmpty(),
            definitions = definitions
        )
    }

    private fun getDefinition(wordDefinitionId: String): WordDefinition {
        val content = database.wordDefinitionTranslationRelationQueries.getByWordDefinitionId(wordDefinitionId).executeAsList().map { wordDefinitionTranslationRelation ->
            database.wordDefinitionTranslationQueries.getById(wordDefinitionTranslationRelation.wordDefinitionTranslationId).executeAsOne()
        }.first {
            it.languagesCode == getSelectedTranslationLanguage().code
        }.content

        val subDefinitions = database.wordDefinitionSubDefinitionRelationQueries.getByWordDefinitionId(wordDefinitionId).executeAsList().map {
            getSubDefinition(it.wordSubDefinitionId)
        }

        val relatedWords = database.wordDefinitionWordRelatedRelationQueries.getByWordDefinitionId(wordDefinitionId).executeAsList().map {
            getWordRelated(it.wordRelatedId)
        }

        return WordDefinition(
            id = wordDefinitionId,
            content = content.orEmpty(),
            subDefinitions = subDefinitions,
            relatedWords = relatedWords
        )
    }

    private fun getSubDefinition(wordSubDefinitionId: String): WordSubDefinitions {
        val content = database.wordSubDefinitionTranslationRelationQueries.getByWordSubDefinitionId(wordSubDefinitionId).executeAsList().map { wordSubDefinitionTranslationRelation ->
            database.wordSubDefinitionTranslationQueries.getById(wordSubDefinitionTranslationRelation.wordSubDefinitionTranslationId).executeAsOne()
        }.first {
            it.languagesCode == getSelectedTranslationLanguage().code
        }.content

        val examples = database.wordSubDefinitionExampleRelationQueries.getByWordSubDefinitionId(wordSubDefinitionId).executeAsList().map {
            getExample(it.exampleId)
        }

        return WordSubDefinitions(
            id = wordSubDefinitionId,
            content = content.orEmpty(),
            examples = examples
        )
    }

    private fun getWordRelated(wordRelatedId: String): WordRelated {
        val wordRelated = database.wordRelatedQueries.getById(wordRelatedId).executeAsOne()

        return WordRelated(
            id = wordRelated.id,
            content = wordRelated.content.orEmpty(),
            strong = wordRelated.strong ?: false,
            type = wordRelated.type.orEmpty()
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
        val exampleData = database.exampleQueries.getById(exampleId).executeAsOneOrNull()
            ?: throw Exception("No example found for the given ID or missing translations")

        return Example(
            id = exampleData.id,
            content = exampleData.content,
        )
    }

    private fun MessageDetailDataResponse.cache() {
        database.messageQueries.insert(
            id = id,
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
                    val example = exampleResponse.exampleData

                    database.exampleQueries.insert(
                        id = example.id,
                        content = example.content
                    )

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

            messageTranslation.vocabularies.forEach { vocabularyResponse ->
                val vocabulary = vocabularyResponse

                database.vocabularyQueries.insert(
                    id = vocabulary.id,
                    word = vocabulary.word,
                    content = vocabulary.content,
                    lemma = vocabulary.lemma,
                    beginOffset = vocabulary.beginOffset.toLong(),
                    partOfSpeech = vocabulary.partOfSpeech,
                    aspect = vocabulary.aspect,
                    case = vocabulary.case,
                    form = vocabulary.form,
                    tense = vocabulary.tense,
                    voice = vocabulary.voice,
                    mood = vocabulary.mood,
                    number = vocabulary.number,
                    person = vocabulary.person,
                    gender = vocabulary.gender,
                    dependency = vocabulary.dependency,
                    dependencyType = vocabulary.dependencyType,
                )

                database.messageTranslationVocabularyRelationQueries.insert(
                    messageTranslationId = messageTranslation.id,
                    vocabularyId = vocabulary.id,
                )
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

        val wordIds = message.translations.flatMap { translation ->
            translation.vocabularies.mapNotNull { vocabulary ->
                vocabulary.word
            }
        }

        fetchWords(wordIds)
    }

    override suspend fun fetchWords(ids: List<String>) {
        val words = chatRemote.getWords(ids)

        words.cache()
    }

    private fun WordsResponse.cache(){
        this.data.forEach { wordResponse ->
            database.wordQueries.insert(
                id = wordResponse.id,
                content = wordResponse.content,
            )

            wordResponse.meanings.forEach { meaning ->

                database.wordMeaningQueries.insert(
                    id = meaning.wordMeaningId.id,
                    partOfSpeech = meaning.wordMeaningId.partOfSpeech,
                )

                meaning.wordMeaningId.definitions.forEach { definition ->
                    database.wordDefinitionQueries.insert(
                        id = definition.wordDefinitionId.id
                    )

                    database.wordMeaningDefinitionRelationQueries.insert(
                        wordMeaningId = meaning.wordMeaningId.id,
                        wordDefinitionId = definition.wordDefinitionId.id,
                    )

                    definition.wordDefinitionId.translations.forEach { translation ->
                        database.wordDefinitionTranslationQueries.insert(
                            id = translation.id,
                            content = translation.content,
                            languagesCode = translation.languagesCode,
                        )

                        database.wordDefinitionTranslationRelationQueries.insert(
                            wordDefinitionId = definition.wordDefinitionId.id,
                            wordDefinitionTranslationId = translation.id,
                        )
                    }

                    definition.wordDefinitionId.subDefinitions.forEach { subDefinition ->
                        database.wordSubDefinitionQueries.insert(
                            id = subDefinition.wordSubDefinitionId.id,
                        )

                        subDefinition.wordSubDefinitionId.translations.forEach { translation ->
                            database.wordSubDefinitionTranslationQueries.insert(
                                id = translation.id,
                                content = translation.content,
                                languagesCode = translation.languagesCode,
                            )

                            database.wordSubDefinitionTranslationRelationQueries.insert(
                                wordSubDefinitionId = subDefinition.wordSubDefinitionId.id,
                                wordSubDefinitionTranslationId = translation.id,
                            )
                        }

                        subDefinition.wordSubDefinitionId.examples.forEach { example ->
                            database.exampleQueries.insert(
                                id = example.exampleData.id,
                                content = example.exampleData.content,
                            )

                            database.wordSubDefinitionExampleRelationQueries.insert(
                                wordSubDefinitionId = subDefinition.wordSubDefinitionId.id,
                                exampleId = example.exampleData.id,
                            )
                        }

                        database.wordDefinitionSubDefinitionRelationQueries.insert(
                            wordDefinitionId = definition.wordDefinitionId.id,
                            wordSubDefinitionId = subDefinition.wordSubDefinitionId.id,
                        )
                    }

                    definition.wordDefinitionId.relatedWords.forEach { relatedWord ->
                        database.wordRelatedQueries.insert(
                            id = relatedWord.wordRelatedId.id,
                            content = relatedWord.wordRelatedId.content,
                            strong = relatedWord.wordRelatedId.strong,
                            type = relatedWord.wordRelatedId.type,
                        )

                        database.wordDefinitionWordRelatedRelationQueries.insert(
                            wordDefinitionId = definition.wordDefinitionId.id,
                            wordRelatedId = relatedWord.wordRelatedId.id,
                        )
                    }

                    database.wordMeaningDefinitionRelationQueries.insert(
                        wordMeaningId = meaning.wordMeaningId.id,
                        wordDefinitionId = definition.wordDefinitionId.id,
                    )
                }

                database.wordMeaningRelationQueries.insert(
                    wordId = wordResponse.id,
                    wordMeaningId = meaning.wordMeaningId.id,
                )
            }
        }
    }

    override suspend fun fetchFirstReplyDetail(messageId: String): Message {
        val message = chatRemote.getFirstReplyOptionDetail(messageId).data.first().relatedMessageId

        message.cache()

        val words = message.translations.flatMap { translation ->
            translation.vocabularies.mapNotNull { vocabulary ->
                vocabulary.word
            }
        }

        fetchWords(words)

        val messageTargetLanguage = message.translations.first { translation ->
            translation.languagesCode == getSelectedTargetLanguage().code
        }

        val messageTranslationLanguage = message.translations.first { translation ->
            translation.languagesCode == getSelectedTranslationLanguage().code
        }

        val articles = messageTargetLanguage.articles.map { article ->
            article.toArticle(getSelectedTranslationLanguage().code)
        }

        val vocabularies = messageTargetLanguage.vocabularies.map { vocabulary ->
            vocabulary.toVocabulary()
        }

        val expressions = messageTargetLanguage.expressions.map { expression ->
            expression.toExpression(getSelectedTranslationLanguage().code)
        }

        return Message(
            id = message.id,
            isUserSent = message.userSent,
            content = messageTargetLanguage.content,
            articles = articles,
            expressions = expressions,
            translation = messageTranslationLanguage.content,
            vocabularies = vocabularies,
        )
    }

    private fun ExampleResponse.toExample(): Example {
        return Example(
            id = exampleData.id,
            content = exampleData.content
        )
    }

    private fun VocabularyResponse.toVocabulary(): Vocabulary {
        return Vocabulary(
            id = id,
            word = word,
            content = content,
            lemma = lemma,
            beginOffset = beginOffset,
            partOfSpeech = partOfSpeech,
            aspect = aspect,
            case = case,
            form = form,
            gender = gender,
            mood = mood,
            number = number,
            person = person,
            tense = tense,
            voice = voice,
            dependency = dependency,
            dependencyType = dependencyType,
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

    private fun ExpressionResponse.toExpression(translationLanguage: String): Expression {
        val description = expressionId.translations.first { translation ->
            translation.languagesCode == translationLanguage
        }.description

        val examples = expressionId.examples.map { example ->
            example.toExample()
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

