package com.example.myapplication.repository

import android.content.Context

interface BookContract {
    interface Repo{
        fun parseLofterAndSave(url: String, context: Context,folder:String="lofter")
        fun parseCzBooksAndSave(url: String = "",folder:String="book")
        fun parseNovelAllBooksAndSave(url: String,folder:String="book")
        fun parseNovelOneBookAndSave(wh: List<String>, nowInt: Int = 0,folder:String="book")
    }
}