package com.example.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.myapplication.R
import com.example.myapplication.repository.BookDownloadRepository
import com.example.myapplication.viewmodel.BookDownloadViewModel
import com.example.myapplication.viewmodel.BookDownloadViewModelFactory
import org.w3c.dom.Text

class BookDownloadActivity : AppCompatActivity() {
    private val factory = BookDownloadViewModelFactory(BookDownloadRepository())
    private val viewModel: BookDownloadViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_download)
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
                    viewModel.parseLofterAndSave(url,this)
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