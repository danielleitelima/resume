package com.danielleitelima.resume

import android.app.Application
import com.danielleitelima.resume.foundation.appDependencyModule
import com.danielleitelima.resume.home.data.dataDependencyModule
import com.danielleitelima.resume.home.domain.foundation.domainDependencyModule
import com.danielleitelima.resume.home.presentation.foundation.presentationDependencyModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ResumeApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ResumeApplication)
            modules(
                appDependencyModule,
                dataDependencyModule,
                domainDependencyModule,
                presentationDependencyModule
            )
        }
    }
}