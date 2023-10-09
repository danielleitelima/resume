package com.danielleitelima.resume.domain

import com.danielleitelima.resume.domain.model.Resume

interface ResumeRepository {

    suspend fun get(): Resume

}