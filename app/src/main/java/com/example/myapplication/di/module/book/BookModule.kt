package com.example.myapplication.di.module.book

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.di.annotation.book.BookScope
import com.example.myapplication.repository.BookContract
import com.example.myapplication.repository.BookDownloadRepository
import dagger.Module
import dagger.Provides


@Module
class BookModule constructor(private val activity: AppCompatActivity) {

    @Provides
    @BookScope
    fun provideActivity(): AppCompatActivity {
        return activity
    }

    @Provides
    @BookScope
    fun provideContext(): Context {
        return activity
    }

    @Provides
    @BookScope
    fun provideBookRepository(
       repo:BookDownloadRepository
    ): BookContract.Repo {
        return repo
    }
}