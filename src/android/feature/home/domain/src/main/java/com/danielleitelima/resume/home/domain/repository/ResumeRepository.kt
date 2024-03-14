package com.danielleitelima.resume.home.domain.repository

import com.danielleitelima.resume.home.domain.model.Resume

interface ResumeRepository {

    suspend fun get(): Resume

}