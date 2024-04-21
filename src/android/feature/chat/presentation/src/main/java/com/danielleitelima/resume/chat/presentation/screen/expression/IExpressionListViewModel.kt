package com.danielleitelima.resume.chat.presentation.screen.expression

import androidx.lifecycle.viewModelScope
import com.danielleitelima.resume.chat.domain.usecase.GetMessageDetail
import kotlinx.coroutines.launch

class IExpressionListViewModel(
    val getMessageDetail: GetMessageDetail
) : ExpressionListViewModel() {
    override fun setInitialState(): ExpressionListContract.State {
        return ExpressionListContract.State()
    }

    override fun handleEvents(event: ExpressionListContract.Event) {
        when (event) {
            is ExpressionListContract.Event.LoadExpressions -> {
                viewModelScope.launch {
                    val messageDetail = getMessageDetail(
                        messageId = event.messageId
                    )

                    setState {
                        copy(
                            expressions = messageDetail.expressions
                        )
                    }
                }
            }
        }
    }
}
