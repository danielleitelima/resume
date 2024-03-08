package com.danielleitelima.resume.data.foundation

import com.danielleitelima.resume.data.IResumeRepository
import com.danielleitelima.resume.data.datasource.remote.ResumeAPI
import com.danielleitelima.resume.domain.repository.ResumeRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataDependencyModule = module {
    factory<ResumeRepository> { IResumeRepository(get()) }

    single {
        val okHttpClient = OkHttpClient.Builder()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://danielleitelima.github.io/resume/")
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
        retrofit.build()
    }

    single { get<Retrofit>().create(ResumeAPI::class.java) }
}
