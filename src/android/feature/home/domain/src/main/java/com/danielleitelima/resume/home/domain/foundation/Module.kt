package com.danielleitelima.resume.home.domain.foundation

import com.danielleitelima.resume.home.domain.usecase.GetResume
import org.koin.dsl.module

val homeDomainModule = module {
    factory { GetResume(get()) }
}
