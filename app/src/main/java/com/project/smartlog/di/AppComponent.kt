package com.project.smartlog.di

import com.project.smartlog.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
}