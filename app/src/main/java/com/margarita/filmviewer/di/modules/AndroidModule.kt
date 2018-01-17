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

    /**
     * Allow the application context to be injected
     * but require that it be annotated with [@Annotation][ForApplication]
     * to explicitly differentiate it from an activity context.
     */
    @Provides @Singleton @ForApplication
    fun provideApplicationContext(): Context = application
}