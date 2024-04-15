package com.danielleitelima.resume.chat.presentation.route.message.detail

import com.danielleitelima.resume.foundation.presentation.foundation.BaseViewModel

abstract class MessageDetailViewModel :
    BaseViewModel<MessageDetailContract.Event, MessageDetailContract.State, MessageDetailContract.Effect>()
