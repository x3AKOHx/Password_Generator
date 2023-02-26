package com.example.passwordgenerator.di

import android.content.Context
import com.example.passwordgenerator.data.AppDatabase
import com.example.passwordgenerator.data.PassDao
import com.example.passwordgenerator.data.RandomPasswordGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun providePlantDao(appDatabase: AppDatabase): PassDao {
        return appDatabase.passDao()
    }

    @Provides
    fun provideRandomPasswordGenerator(): RandomPasswordGenerator {
        return RandomPasswordGenerator()
    }
}