package com.margarita.filmviewer.mock

import com.margarita.filmviewer.di.components.ApplicationComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestRestModule::class])
interface TestApplicationComponent : ApplicationComponent