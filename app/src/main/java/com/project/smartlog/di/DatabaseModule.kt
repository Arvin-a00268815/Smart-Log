package com.project.smartlog.di

import android.arch.persistence.room.Room
import android.content.Context
import com.project.smartlog.repository.database.DatabaseAccess
import com.project.smartlog.ui.MainActivity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Provides
    fun providesContext(): Context {
        return MainActivity.getInstance()
    }

    @Singleton
    @Provides
    fun provideDatabaseInstance(context: Context) : DatabaseAccess {
        return Room.databaseBuilder(context, DatabaseAccess::class.java, "smart_log-db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}