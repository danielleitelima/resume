package com.danielleitelima.resume.data.foundation

import com.danielleitelima.resume.data.FakeResumeRepository
import com.danielleitelima.resume.domain.repository.ResumeRepository
import org.koin.dsl.module

val dataDependencyModule = module {
    factory<ResumeRepository> { FakeResumeRepository() }
}
