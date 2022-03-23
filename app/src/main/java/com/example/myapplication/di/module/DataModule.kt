package com.example.myapplication.di.module

import com.example.myapplication.repository.BookDownloadRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun providesRepository(): BookDownloadRepository =
        BookDownloadRepository()
}