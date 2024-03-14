package com.danielleitelima.resume.activity

import com.danielleitelima.resume.foundation.presentation.foundation.BaseViewModel


abstract class MainViewModel :
    BaseViewModel<MainContract.Event, MainContract.State, MainContract.Effect>()
