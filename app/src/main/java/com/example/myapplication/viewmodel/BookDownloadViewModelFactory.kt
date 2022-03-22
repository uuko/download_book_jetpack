package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.BookDownloadRepository

class BookDownloadViewModelFactory(private val dataModel: BookDownloadRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(BookDownloadViewModel::class.java)) {
            return BookDownloadViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}