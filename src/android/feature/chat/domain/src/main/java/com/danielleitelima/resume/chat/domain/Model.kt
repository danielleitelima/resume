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

data class MessageDetail(
    val id : String,
    val isUserSent: Boolean,
    val translation: String,
    val content: String,
    val expressions: List<Expression>,
    val articles: List<Article>,
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
    val definitions: List<WordDefinition>
)

data class WordDefinition(
    val id: String,
    val content: String,
    val subDefinitions: List<WordSubDefinitions>,
    val relatedWords: List<WordRelated>
)

data class WordRelated(
    val id: String,
    val content: String,
    val isStrong: Boolean,
    val type: String
)

data class WordSubDefinitions(
    val id: String,
    val content: String,
    val examples: List<Example>
)

data class Example(
    val id: String,
    val content: String
)

data class Expression(
    val id: String,
    val content: String,
    val description: String,
    val examples: List<Example>
)

data class Article(
    val id: String,
    val dateCreated: Long,
    val dateUpdated: Long?,
    val readTime: Int,
    val title: String,
    val teaser: String,
    val content: String
){
    val lastUpdate: Long
        get() = dateUpdated ?: dateCreated
}
