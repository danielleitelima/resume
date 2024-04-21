package com.danielleitelima.resume.chat.presentation.screen.message.list

import androidx.lifecycle.viewModelScope
import com.danielleitelima.resume.chat.domain.usecase.GetChat
import com.danielleitelima.resume.chat.domain.usecase.GetMessageOptions
import com.danielleitelima.resume.chat.domain.usecase.GetSentMessages
import com.danielleitelima.resume.chat.domain.usecase.SelectMessageOption
import kotlinx.coroutines.launch


class IMessageListViewModel(
    private val getSentMessage: GetSentMessages,
    private val getMessageOptions: GetMessageOptions,
    private val selectMessageOption: SelectMessageOption,
    private val getChat: GetChat
) : MessageListViewModel() {

    override fun setInitialState(): MessageListContract.State {
        return MessageListContract.State()
    }

    override fun handleEvents(event: MessageListContract.Event) {
        when (event) {
            is MessageListContract.Event.LoadMessages -> {
                viewModelScope.launch {
                    getSentMessage(event.chatId).collect {
                        setState {
                            copy(
                                sentMessages = it
                            )
                        }
                    }
                }

                viewModelScope.launch {
                    getMessageOptions(event.chatId).collect {
                        setState {
                            copy(
                                messageOptions = it
                            )
                        }
                    }
                }

                viewModelScope.launch {
                    getChat(event.chatId).collect {
                        setState {
                            copy(
                                chat = it
                            )
                        }
                    }
                }
            }

            is MessageListContract.Event.SelectMessageOption -> {
                viewModelScope.launch {
                    val chatId = state.value.chat?.id ?: return@launch

                    selectMessageOption(chatId = chatId, messageOptionId = event.messageId)
                }
            }
        }
    }
}
