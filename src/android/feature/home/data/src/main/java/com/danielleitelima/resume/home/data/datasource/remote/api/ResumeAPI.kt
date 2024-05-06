package com.danielleitelima.resume.home.data.datasource.remote.api

import com.danielleitelima.resume.home.data.datasource.remote.dto.ResumeDTO
import retrofit2.http.GET

interface ResumeAPI {
    @GET("main.json")
    suspend fun getResume(): ResumeDTO
}