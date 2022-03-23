package com.example.myapplication.repository

import android.content.Context

interface BookContract {
    interface Repo{
        fun parseLofterAndSave(url: String, context: Context)
        fun parseCzBooksAndSave(url: String = "")
        fun parseNovelAllBooksAndSave(url: String)
        fun parseNovelOneBookAndSave(wh: List<String>, nowInt: Int = 0)
    }
}