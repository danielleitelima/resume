package com.danielleitelima.resume.domain.usecase

import com.danielleitelima.resume.domain.repository.ResumeRepository

class GetResume(
    private val repository: ResumeRepository
) {
    suspend operator fun invoke() = repository.get()
}