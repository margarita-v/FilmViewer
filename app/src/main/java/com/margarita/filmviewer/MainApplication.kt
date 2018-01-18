package com.margarita.filmviewer

import android.app.Application
import com.margarita.filmviewer.di.components.ApplicationComponent
import com.margarita.filmviewer.di.components.DaggerApplicationComponent
import com.margarita.filmviewer.di.modules.AndroidModule

class MainApplication: Application() {

    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        initComponent()
    }

    private fun initComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .androidModule(AndroidModule(this)).build()
    }
}