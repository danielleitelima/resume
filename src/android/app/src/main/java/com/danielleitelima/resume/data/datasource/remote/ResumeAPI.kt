package com.danielleitelima.resume.data.datasource.remote

import retrofit2.http.GET

interface ResumeAPI {
    @GET("main.json")
    suspend fun getResume(): ResumeDTO
}