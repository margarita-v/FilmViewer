package com.margarita.filmviewer.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module for Android-specific dependencies
 */
@Module
class AndroidModule(private val application: Application) {

    @Provides @Singleton
    fun provideApplicationContext(): Context = application
}