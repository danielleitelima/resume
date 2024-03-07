package com.danielleitelima.resume.presentation.foundation

import android.app.Application
import com.danielleitelima.resume.data.foundation.dataDependencyModule
import com.danielleitelima.resume.domain.foundation.domainDependencyModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ResumeApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ResumeApplication)
            modules(
                dataDependencyModule,
                domainDependencyModule,
                presentationDependencyModule
            )
        }
    }
}