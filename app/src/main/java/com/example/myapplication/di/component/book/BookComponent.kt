package com.example.myapplication.di.component.book

import com.example.myapplication.di.annotation.book.BookScope
import com.example.myapplication.di.component.ApplicationComponent
import com.example.myapplication.di.module.ViewModelModule
import com.example.myapplication.di.module.book.BookModule
import com.example.myapplication.view.BookDownloadActivity
import dagger.Component


@BookScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [BookModule::class]
)
interface BookComponent {
    fun inject(activity: BookDownloadActivity)
}