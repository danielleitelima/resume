package com.danielleitelima.resume.presentation.foundation

import com.danielleitelima.resume.presentation.activity.IMainViewModel
import com.danielleitelima.resume.presentation.activity.MainViewModel
import com.danielleitelima.resume.presentation.route.home.HomeViewModel
import com.danielleitelima.resume.presentation.route.home.IHomeViewModel
import org.koin.dsl.module

val presentationDependencyModule = module {
    factory<MainViewModel> { IMainViewModel() }
    factory<HomeViewModel> { IHomeViewModel(get()) }
}
