package com.danielleitelima.resume.chat.data.foundation

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.danielleitelima.resume.chat.data.Database
import com.danielleitelima.resume.chat.data.datasource.remote.ChatRemote
import com.danielleitelima.resume.chat.data.datasource.remote.IChatRemote
import com.danielleitelima.resume.chat.data.repository.IChatRepository
import com.danielleitelima.resume.chat.domain.repository.ChatRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val chatDataModule = module {
    single<ChatRepository> { IChatRepository(get(), get()) }

    single<ChatRemote> { IChatRemote(get()) }

    single<HttpClient> {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }

    single<Database> {
        val driver = AndroidSqliteDriver(Database.Schema, androidContext(), "database.db")
        Database.invoke(driver)
    }
}
