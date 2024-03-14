package com.danielleitelima.resume.home.presentation.foundation

import com.danielleitelima.resume.home.presentation.route.home.HomeViewModel
import com.danielleitelima.resume.home.presentation.route.home.IHomeViewModel
import org.koin.dsl.module

val presentationDependencyModule = module {
    factory<HomeViewModel> { IHomeViewModel(get()) }
}
