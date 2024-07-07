package com.danielleitelima.resume.chat.domain

data class SelectedLanguages(
    val target: Language,
    val translation: Language,
)

data class InitialData(
    val targetsLanguages: List<Language>,
    val translationLanguages: List<Language>,
    val chats: List<OpenChat>,
)

data class Language(
    val code: String,
    val name: String,
    val flagUrl: String,
    val selected: Boolean,
)

data class ActiveChat(
    val openChat: OpenChat,
    val currentMessageOptions: List<MessageOption>? = null,
)

data class Chat(
    val id: String,
    val title: String,
)

data class OpenChat(
    val id: String,
    val title: String,
    val history: List<SentMessage>,
)

data class SentMessage(
    val id: String,
    val messageId: String,
    val isUserSent: Boolean,
    val timestamp: Long,
    val content: String
)

data class MessageOption(
    val id: String,
    val isUserSent: Boolean,
    val content: String
)

data class Message(
    val id : String,
    val isUserSent: Boolean,
    val translation: String,
    val content: String,
    val vocabularies: List<Vocabulary>
)

data class Vocabulary(
    val id: String,
    val content: String,
    val lemma: String,
    val beginOffset: Int,
    val word: String?,
    val partOfSpeech: String,
    val aspect: String,
    val case: String,
    val form: String,
    val gender: String,
    val mood: String,
    val number: String,
    val person: String,
    val tense: String,
    val voice: String,
    val dependency: String?,
    val dependencyType: String?,
)

data class Word(
    val id: String,
    val content: String,
    val meanings: List<WordMeaning>
)

data class WordMeaning(
    val id: String,
    val partsOfSpeech: String,
    val content: String,
    val definitions: List<WordDefinition>,
    val relatedWords: List<WordRelated>
)

data class WordDefinition(
    val id: String,
    val content: String,
    val examples: List<WordExample>
)

data class WordRelated(
    val id: String,
    val content: String,
    val strong: Boolean,
    val type: String
)

data class WordExample(
    val id: String,
    val content: String
)