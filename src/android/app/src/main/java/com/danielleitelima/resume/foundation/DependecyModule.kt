package com.danielleitelima.resume.foundation

import com.danielleitelima.resume.activity.IMainViewModel
import com.danielleitelima.resume.activity.MainViewModel
import com.danielleitelima.resume.foundation.presentation.foundation.TextToSpeechManager
import org.koin.dsl.module

val appDependencyModule = module {
    factory<MainViewModel> { IMainViewModel() }
    single { TextToSpeechManager() }
}
