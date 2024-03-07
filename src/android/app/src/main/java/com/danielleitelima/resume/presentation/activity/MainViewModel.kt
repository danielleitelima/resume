package com.danielleitelima.resume.presentation.activity

import com.danielleitelima.resume.presentation.foundation.BaseViewModel


abstract class MainViewModel :
    BaseViewModel<MainContract.Event, MainContract.State, MainContract.Effect>()
