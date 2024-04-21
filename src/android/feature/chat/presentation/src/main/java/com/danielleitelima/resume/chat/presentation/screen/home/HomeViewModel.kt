package com.danielleitelima.resume.chat.presentation.screen.home

import com.danielleitelima.resume.foundation.presentation.foundation.BaseViewModel

abstract class HomeViewModel :
    BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>()
