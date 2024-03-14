package com.danielleitelima.resume.home.domain.usecase

import com.danielleitelima.resume.home.domain.repository.ResumeRepository

class GetResume(
    private val repository: ResumeRepository
) {
    suspend operator fun invoke() = repository.get()
}