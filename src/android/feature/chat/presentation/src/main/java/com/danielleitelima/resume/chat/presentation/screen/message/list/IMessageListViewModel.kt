package com.danielleitelima.resume.chat.presentation.screen.message.list

import androidx.lifecycle.viewModelScope
import com.danielleitelima.resume.chat.domain.usecase.GetActiveChat
import com.danielleitelima.resume.chat.domain.usecase.RollbackToMessage
import com.danielleitelima.resume.chat.domain.usecase.SelectMessageOption
import kotlinx.coroutines.launch

class IMessageListViewModel(
    private val selectMessageOption: SelectMessageOption,
    private val getActiveChat: GetActiveChat,
    private val rollbackToMessage: RollbackToMessage
) : MessageListViewModel() {

    override fun setInitialState(): MessageListContract.State {
        return MessageListContract.State()
    }

    override fun handleEvents(event: MessageListContract.Event) {
        when (event) {
            is MessageListContract.Event.SelectMessageOption -> {
                viewModelScope.launch {
                    val chatId = state.value.openChat?.id ?: return@launch
                    selectMessageOption(chatId = chatId, messageOptionId = event.messageId)
                }
            }

            is MessageListContract.Event.LoadChat -> {
                viewModelScope.launch {
                    getActiveChat(event.chatId).collect {
                        setState {
                            copy(
                                isLoading = false,
                                openChat = it.openChat,
                                messageOptions = it.currentMessageOptions
                            )
                        }
                    }
                }
            }

            is MessageListContract.Event.RollbackToMessage -> {
                viewModelScope.launch {
                    val chatId = state.value.openChat?.id ?: return@launch
                    rollbackToMessage(chatId = chatId, messageId = event.messageId)
                }
            }
        }
    }
}
