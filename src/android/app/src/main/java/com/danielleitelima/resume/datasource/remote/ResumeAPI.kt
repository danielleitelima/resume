package com.danielleitelima.resume.datasource.remote

import retrofit2.http.GET

interface ResumeAPI {

    @GET("data.json")
    suspend fun get(): ResumeDTO

}