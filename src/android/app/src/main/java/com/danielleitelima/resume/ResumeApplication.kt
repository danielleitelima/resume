package com.danielleitelima.resume

import android.app.Application
import com.danielleitelima.resume.chat.data.foundation.chatDataModule
import com.danielleitelima.resume.chat.domain.foundation.chatDomainModule
import com.danielleitelima.resume.chat.presentation.foundation.chatPresentationModule
import com.danielleitelima.resume.foundation.appDependencyModule
import com.danielleitelima.resume.home.data.foundation.homeDataModule
import com.danielleitelima.resume.home.domain.foundation.homeDomainModule
import com.danielleitelima.resume.home.presentation.foundation.homePresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ResumeApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ResumeApplication)

            val homeModules = listOf(
                homeDataModule,
                homeDomainModule,
                homePresentationModule
            )

            val chatModules = listOf(
                chatDataModule,
                chatDomainModule,
                chatPresentationModule
            )

            modules(
                appDependencyModule,
                *homeModules.toTypedArray(),
                *chatModules.toTypedArray(),
            )
        }
    }
}