package com.margarita.filmviewer.di.modules

import com.margarita.filmviewer.rest.RestClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RestModule(private val restClient: RestClient) {

    @Provides @Singleton
    internal fun provideRestClient() = restClient
}