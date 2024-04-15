package com.danielleitelima.resume.chat.domain

data class Chat(
    val id: String,
    val title: String,
    val initialMessageOptions: List<MessageOption>,
    val history: List<MessageDetail>,
)

data class AvailableChat(
    val id: String,
    val title: String,
)

data class ActiveChat(
    val id: String,
    val title: String,
    val lastMessageTimestamp: Long,
)

data class MessageOption(
    val id: String,
    val content: String
)

data class SentMessage(
    val id: String,
    val content: String,
    val isUserSent: Boolean,
    val timestamp: Long
)

data class MessageDetail(
    val id : String,
    val timestamp: Long,
    val isUserSent: Boolean,
    val content: String,
    val translation: String,
    val sections: List<Section>,
    val expressions: List<Expression>,
    val relatedArticles: List<RelatedArticle>,
    val replyOptionsIds: List<String>,
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
    val position: Int,
    val content: String,
    val meaning: Meaning,
    val otherMeanings: List<Meaning>
)

data class HighlightedRange(
    val sectionId: String,
    val start: Int,
    val end: Int
)

data class RelatedArticle(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val readTime: String,
    val content: String
)

data class Meaning(
    val id: String,
    val content: String,
    val examples: List<Example>
)

data class Expression(
    val id: String,
    val content: String,
    val description: String,
    val examples: List<Example>
)

data class Example(
    val id : String,
    val content: String,
    val translation: String
)