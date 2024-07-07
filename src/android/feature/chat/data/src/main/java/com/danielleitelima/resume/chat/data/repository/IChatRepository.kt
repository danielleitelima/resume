package com.danielleitelima.resume.chat.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.danielleitelima.resume.chat.data.Database
import com.danielleitelima.resume.chat.data.datasource.remote.AvailableLanguagesResponse
import com.danielleitelima.resume.chat.data.datasource.remote.ChatRemote
import com.danielleitelima.resume.chat.data.datasource.remote.ChatResponse
import com.danielleitelima.resume.chat.data.datasource.remote.MessageDetailDataResponse
import com.danielleitelima.resume.chat.data.datasource.remote.VocabularyResponse
import com.danielleitelima.resume.chat.data.datasource.remote.WordsResponse
import com.danielleitelima.resume.chat.domain.Chat
import com.danielleitelima.resume.chat.domain.Language
import com.danielleitelima.resume.chat.domain.Message
import com.danielleitelima.resume.chat.domain.MessageOption
import com.danielleitelima.resume.chat.domain.OpenChat
import com.danielleitelima.resume.chat.domain.SentMessage
import com.danielleitelima.resume.chat.domain.Vocabulary
import com.danielleitelima.resume.chat.domain.Word
import com.danielleitelima.resume.chat.domain.WordDefinition
import com.danielleitelima.resume.chat.domain.WordExample
import com.danielleitelima.resume.chat.domain.WordMeaning
import com.danielleitelima.resume.chat.domain.WordRelated
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
                val chatTranslation = database.chatTranslationQueries.getByChatId(chat.id).executeAsList().first { chatTranslation ->
                    chatTranslation.languagesCode == targetLanguage.first().code
                }

                val title = chatTranslation.title

                val messages = chat.messages.split(",")
                val chatMessages = chat.relationIds.split(",")
                val timestamps = chat.timestamps.split(",").mapNotNull { it.toLongOrNull() }

                val history = messages.zip(chatMessages.zip(timestamps)) { message, pair ->
                    val content = database.messageTranslationQueries.getByMessageId(message).executeAsList().first { messageTranslation ->
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

            val chatTranslation = database.chatTranslationQueries.getByChatId(chat.id).executeAsList().first { chatTranslation ->
                chatTranslation.languagesCode == getSelectedTargetLanguage().code
            }

            val title = chatTranslation.title

            val messages = chat.messages.split(",")
            val chatMessages = chat.relationIds.split(",")
            val timestamps = chat.timestamps.split(",").mapNotNull { timestamp -> timestamp.toLongOrNull() }

            val history = messages.zip(chatMessages.zip(timestamps)) { message, pair ->
                val content = database.messageTranslationQueries.getByMessageId(message).executeAsList().first { messageTranslation ->
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

        val messageTranslationLanguage = database.messageTranslationQueries.getByMessageId(messageId).executeAsList().first {
            it.languagesCode == getSelectedTranslationLanguage().code
        }

        val messageTargetLanguage = database.messageTranslationQueries.getByMessageId(messageId).executeAsList().first {
            it.languagesCode == getSelectedTargetLanguage().code
        }

        val vocabularies = database.vocabularyQueries.getByMessageTranslationId(messageTargetLanguage.id).executeAsList().map {
            getVocabulary(it)
        }

        return Message(
            id = message.id,
            content = messageTargetLanguage.content,
            isUserSent = message.userSent ?: false,
            translation = messageTranslationLanguage.content,
            vocabularies = vocabularies,
        )
    }

    private fun getVocabulary(vocabulary: database.Vocabulary): Vocabulary {
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

        val meanings = database.wordMeaningQueries.getByWordId(wordId).executeAsList().map {
            getWordMeaning(it)
        }

        return Word(
            id = word.id,
            content = word.content.orEmpty(),
            meanings = meanings
        )
    }

    private fun getWordMeaning(wordMeaning: database.WordMeaning): WordMeaning {
        val content = database.wordMeaningTranslationQueries.getByWordMeaningId(wordMeaning.id).executeAsList().first {
            it.languagesCode == getSelectedTranslationLanguage().code
        }.content

        val definitions = database.wordDefinitionQueries.getByWordMeaningId(wordMeaning.id).executeAsList().map {
            getWordDefinition(it)
        }

        val relatedWords = database.wordRelatedQueries.getByWordMeaningId(wordMeaning.id).executeAsList().map {
            getWordRelated(it)
        }

        return WordMeaning(
            id = wordMeaning.id,
            content = content.orEmpty(),
            partsOfSpeech = wordMeaning.partOfSpeech.orEmpty(),
            definitions = definitions,
            relatedWords = relatedWords
        )
    }

    private fun getWordDefinition(wordDefinition: database.WordDefinition): WordDefinition {
        val content = database.wordDefinitionTranslationQueries.getByWordDefinitionId(wordDefinition.id).executeAsList().first {
            it.languagesCode == getSelectedTranslationLanguage().code
        }.content

        val examples = database.wordExampleQueries.getByWordDefinitionId(wordDefinition.id).executeAsList().map {
            getWordExample(it)
        }

        return WordDefinition(
            id = wordDefinition.id,
            content = content.orEmpty(),
            examples = examples
        )
    }

    private fun getWordRelated(wordRelated: database.WordRelated): WordRelated {
        return WordRelated(
            id = wordRelated.id,
            content = wordRelated.content.orEmpty(),
            strong = wordRelated.strong ?: false,
            type = wordRelated.type.orEmpty()
        )
    }

    private fun getWordExample(wordExample: database.WordExample): WordExample {
        return WordExample(
            id = wordExample.id,
            content = wordExample.content,
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
                message_id = id,
            )

            messageTranslation.vocabularies.forEach { vocabularyResponse ->

                database.vocabularyQueries.insert(
                    id = vocabularyResponse.id,
                    word = vocabularyResponse.word,
                    content = vocabularyResponse.content,
                    lemma = vocabularyResponse.lemma,
                    beginOffset = vocabularyResponse.beginOffset.toLong(),
                    partOfSpeech = vocabularyResponse.partOfSpeech,
                    aspect = vocabularyResponse.aspect,
                    case = vocabularyResponse.case,
                    form = vocabularyResponse.form,
                    tense = vocabularyResponse.tense,
                    voice = vocabularyResponse.voice,
                    mood = vocabularyResponse.mood,
                    number = vocabularyResponse.number,
                    person = vocabularyResponse.person,
                    gender = vocabularyResponse.gender,
                    dependency = vocabularyResponse.dependency,
                    dependencyType = vocabularyResponse.dependencyType,
                    messageTranslationId = messageTranslation.id,
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
                    chat_id = chatResponse.id,
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
        if (ids.isEmpty()) {
            return
        }

        val words = chatRemote.getWords(ids)

        words.cache()
    }

    private fun WordsResponse.cache(){
        this.data.forEach { wordResponse ->
            database.wordQueries.insert(
                id = wordResponse.id,
                content = wordResponse.content,
                languagesCode = wordResponse.languagesCode,
            )

            wordResponse.meanings.forEach { meaning ->

                database.wordMeaningQueries.insert(
                    id = meaning.id,
                    partOfSpeech = meaning.partOfSpeech,
                    wordId = wordResponse.id,
                )

                meaning.translations.forEach { translation ->
                    database.wordMeaningTranslationQueries.insert(
                        id = translation.id,
                        content = translation.content,
                        languagesCode = translation.languagesCode,
                        wordMeaningId = meaning.id,
                    )

                }

                meaning.definitions.forEach { definition ->
                    database.wordDefinitionQueries.insert(
                        id = definition.id,
                        wordMeaningId = meaning.id,
                    )

                    definition.translations.forEach { translation ->
                        database.wordDefinitionTranslationQueries.insert(
                            id = translation.id,
                            content = translation.content,
                            languagesCode = translation.languagesCode,
                            wordDefinitionId = definition.id,
                        )

                    }

                    definition.examples.forEach { wordExample ->
                        database.wordExampleQueries.insert(
                            id = wordExample.id,
                            content = wordExample.content,
                            wordDefinitionId = definition.id,
                        )
                    }

                }

                meaning.relations.forEach { relatedWord ->
                    database.wordRelatedQueries.insert(
                        id = relatedWord.id,
                        content = relatedWord.content,
                        strong = relatedWord.strong,
                        type = relatedWord.type,
                        wordMeaningId = meaning.id,
                    )
                }
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

        val vocabularies = messageTargetLanguage.vocabularies.map { vocabulary ->
            vocabulary.toVocabulary()
        }

        return Message(
            id = message.id,
            isUserSent = message.userSent,
            content = messageTargetLanguage.content,
            translation = messageTranslationLanguage.content,
            vocabularies = vocabularies,
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
                    languagesCode = languageTranslation.languagesCode,
                    translationLanguageId = languageResponse.id,
                )
            }
        }
    }

    private fun AvailableLanguagesResponse.cacheTarget() {
        val currentLanguage = database.targetLanguageQueries.getSelected().executeAsOneOrNull()

        database.targetLanguageQueries.deleteAll()
        database.targetLanguageTranslationQueries.deleteAll()

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
                    languagesCode = languageTranslation.languagesCode,
                    targetLanguageId = languageResponse.id,
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

