package com.danielleitelima.resume.presentation.route.home

import com.danielleitelima.resume.presentation.foundation.BaseViewModel


abstract class HomeViewModel :
    BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>()
