package com.danielleitelima.resume.chat.presentation.screen.message.detail

import androidx.lifecycle.viewModelScope
import com.danielleitelima.resume.chat.domain.usecase.GetMessageDetail
import kotlinx.coroutines.launch


class IMessageDetailViewModel(
    private val getMessageDetail: GetMessageDetail
) : MessageDetailViewModel() {

    override fun setInitialState(): MessageDetailContract.State {
        return MessageDetailContract.State()
    }

    override fun handleEvents(event: MessageDetailContract.Event) {
        when (event) {
            is MessageDetailContract.Event.LoadMessageDetail -> {
                viewModelScope.launch {
                    val messageDetail = getMessageDetail(
                        messageId = event.messageId
                    )

                    setState {
                        copy(
                            messageDetail = messageDetail
                        )
                    }
                }
            }
        }
    }
}
