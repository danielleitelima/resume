package com.danielleitelima.resume.home.presentation.route.home

import com.danielleitelima.resume.foundation.presentation.foundation.BaseViewModel


abstract class HomeViewModel :
    BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>()
