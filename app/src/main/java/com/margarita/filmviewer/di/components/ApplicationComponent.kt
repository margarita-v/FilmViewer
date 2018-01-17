package com.margarita.filmviewer.di.components

import com.margarita.filmviewer.di.modules.AndroidModule
import com.margarita.filmviewer.di.modules.RestModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidModule::class, RestModule::class])
interface ApplicationComponent {
}