package com.example.myapplication.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.di.annotation.ViewModelKey
import com.example.myapplication.viewmodel.BookDownloadViewModel
import com.example.myapplication.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BookDownloadViewModel::class)
    abstract fun bindBookDownloadModel(bookDownloadViewModel: BookDownloadViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}