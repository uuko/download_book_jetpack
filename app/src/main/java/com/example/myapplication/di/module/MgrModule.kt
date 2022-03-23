package com.example.myapplication.di.module

import com.example.myapplication.mgr.CzBookParser
import com.example.myapplication.mgr.LofterParser
import com.example.myapplication.repository.BookDownloadRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MgrModule {
    @Singleton
    @Provides
    fun providesCzBookParser(): CzBookParser =
        CzBookParser()


    @Singleton
    @Provides
    fun providesLofterParser(): LofterParser =
        LofterParser()


}