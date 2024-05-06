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
    val sections: List<Section>,
    val expressions: List<Expression>,
    val articles: List<Article>,
){
    fun getHighlightedRanges(): List<HighlightedRange> {
        val highlightedRanges = mutableListOf<HighlightedRange>()

        sections.forEach { section ->
            var currentPosition = 0
            var count = 0

            while (true) {
                val start = content.indexOf(section.content, currentPosition)
                if (start == -1) break

                val end = start + section.content.length
                currentPosition = end

                if (count == section.position) {
                    highlightedRanges.add(HighlightedRange(sectionId = section.id, start = start, end = end))
                    break
                }
                count++
            }
        }

        return highlightedRanges
    }
}

data class Section(
    val id: String,
    val content: String,
    val position: Int,
    val meanings: List<Meaning>
)

data class HighlightedRange(
    val sectionId: String,
    val start: Int,
    val end: Int
)

data class Meaning(
    val id: String,
    val main: Boolean,
    val content: String,
    val translation: String,
    val examples: List<Example>
)

data class Example(
    val id: String,
    val content: String,
    val translation: String
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
