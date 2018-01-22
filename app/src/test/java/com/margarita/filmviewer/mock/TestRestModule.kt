package com.margarita.filmviewer.mock

import com.margarita.filmviewer.rest.FilmsApi
import dagger.Module
import dagger.Provides
import org.mockito.Mockito

@Module
class TestRestModule {

    @Provides internal fun provideFilmsApi() = Mockito.mock(FilmsApi::class.java)
}