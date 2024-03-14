package com.danielleitelima.resume.foundation

import com.danielleitelima.resume.activity.IMainViewModel
import com.danielleitelima.resume.activity.MainViewModel
import org.koin.dsl.module

val appDependencyModule = module {
    factory<MainViewModel> { IMainViewModel() }
}
