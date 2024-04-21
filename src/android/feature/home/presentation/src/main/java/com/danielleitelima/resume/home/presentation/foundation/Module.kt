package com.danielleitelima.resume.home.presentation.foundation

import androidx.navigation.NavGraphBuilder
import com.danielleitelima.resume.foundation.presentation.foundation.register
import com.danielleitelima.resume.home.presentation.screen.home.HomeScreen
import com.danielleitelima.resume.home.presentation.screen.home.HomeViewModel
import com.danielleitelima.resume.home.presentation.screen.home.IHomeViewModel
import org.koin.dsl.module

fun NavGraphBuilder.registerHomeRoutes() {
    register(HomeScreen)
}

val homePresentationModule = module {
    factory<HomeViewModel> { IHomeViewModel(get()) }
}
