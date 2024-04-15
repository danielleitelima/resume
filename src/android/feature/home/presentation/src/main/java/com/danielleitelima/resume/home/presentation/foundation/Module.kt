package com.danielleitelima.resume.home.presentation.foundation

import androidx.navigation.NavGraphBuilder
import com.danielleitelima.resume.foundation.presentation.foundation.navigation.register
import com.danielleitelima.resume.home.presentation.route.home.HomeRoute
import com.danielleitelima.resume.home.presentation.route.home.HomeViewModel
import com.danielleitelima.resume.home.presentation.route.home.IHomeViewModel
import org.koin.dsl.module

fun NavGraphBuilder.registerHomeRoutes() {
    register(HomeRoute)
}

val homePresentationModule = module {
    factory<HomeViewModel> { IHomeViewModel(get()) }
}
