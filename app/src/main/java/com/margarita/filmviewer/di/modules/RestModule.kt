package com.margarita.filmviewer.di.modules

import com.margarita.filmviewer.rest.FilmsApi
import com.margarita.filmviewer.rest.RestClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RestModule {

    private val restClient: RestClient = RestClient()

    @Provides @Singleton
    internal fun provideRestClient() = restClient

    @Provides @Singleton
    fun provideFilmsApi() = restClient.createService(FilmsApi::class.java)
}