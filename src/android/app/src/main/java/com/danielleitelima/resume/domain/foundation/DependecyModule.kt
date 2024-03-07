package com.danielleitelima.resume.domain.foundation

import com.danielleitelima.resume.domain.usecase.GetResume
import org.koin.dsl.module

val domainDependencyModule = module {
    factory { GetResume(get()) }
}
