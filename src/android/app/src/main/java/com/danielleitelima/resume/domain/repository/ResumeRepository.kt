package com.danielleitelima.resume.domain.repository

import com.danielleitelima.resume.domain.model.Resume

interface ResumeRepository {

    suspend fun get(): Resume

}