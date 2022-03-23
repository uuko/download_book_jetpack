package com.example.myapplication.view

import android.app.Application
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.di.component.book.BookComponent
import com.example.myapplication.di.component.book.DaggerBookComponent
import com.example.myapplication.di.module.book.BookModule
import com.example.myapplication.viewmodel.BookDownloadViewModel
import javax.inject.Inject


class BookDownloadActivity : AppCompatActivity() {
//    private val factory = BookDownloadViewModelFactory(BookDownloadRepository())


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: BookDownloadViewModel by viewModels {
        viewModelFactory
    }
    lateinit var mBookComponent: BookComponent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_download)
        mBookComponent = DaggerBookComponent.builder()
            .bookModule(BookModule(this))
            .applicationComponent((application as App).getApplicationComponent())
            .build()
        mBookComponent.inject(this)


        val submit: Button = findViewById(R.id.submit)
        val submitAll: Button = findViewById(R.id.submitAll)
        val inputText: TextView = findViewById(R.id.text)
        val progress: ProgressBar = findViewById(R.id.progress_circular)
        val loadText: TextView = findViewById(R.id.loadText)

        viewModel.bookAllSize.observe(this) {
            viewModel.parseNovelOneBookAndSave(it.list)
        }
        viewModel.progress.observe(this) {
            loadText.visibility = View.VISIBLE
            if (it.show) {
                progress.visibility = View.VISIBLE


            } else {
                progress.visibility = View.GONE
//                loadText.visibility = View.GONE
            }
            loadText.text = it.text
        }



        submit.setOnClickListener {
            val url = inputText.text.toString()
            if (url.isNotEmpty()) {
                if (url.split("https://")[1].contains("czbooks.net")) {
                    viewModel.parseCzBooksAndSave(url)
                }
                //else if (url.split("https://")[1].contains("www.sto.cx")) {
//                    var mUrl = url.replace("mbook", "book")
//                    parseStoAndSave(url)
//                }
                //
                else {
                    viewModel.parseLofterAndSave(url, this)
                }
            }
        }

        submitAll.setOnClickListener {
            val url = inputText.text.toString()
            if (url.isNotEmpty()) {

                if (url.split("https://")[1].contains("czbooks.net")) {
                    viewModel.parseNovelAllBooksAndSave(url)
                }

//                else if (url.split("https://")[1].contains("www.sto.cx")) {
//                    var mUrl = url.replace("mbook", "book")
//                    parseStoAllBooksAndSave(mUrl)
//                }

                else {
                    Toast.makeText(this, "目前還不支持整本功能", Toast.LENGTH_LONG)
                }
            }
        }

    }
}