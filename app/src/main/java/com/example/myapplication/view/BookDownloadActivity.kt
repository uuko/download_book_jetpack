package com.example.myapplication.view

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
import android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
        if (!checkPermission()){
            requestPermission()
        }
//        if (Environment.isExternalStorageManager()) {
////            internal = File("/sdcard")
////            internalContents = internal.listFiles()
//        } else {
//            val permissionIntent = Intent(ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
//            startActivity(permissionIntent)
//        }

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

            Log.e("onCreate", " viewModel.progress.observe:  ${it.text}")
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


    private fun checkPermission(): Boolean {
        return if (SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val result =
                ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
            val result1 =
                ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
            result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(String.format("package:%s", applicationContext.packageName))
                startActivityForResult(intent, 2296)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivityForResult(intent, 2296)
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(
                this,
                arrayOf(WRITE_EXTERNAL_STORAGE),
                2297
            )
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2296) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // perform action when allow permission success
                } else {
                    Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            2297 -> if (grantResults.isNotEmpty()) {
                val READ_EXTERNAL_STORAGE = grantResults[0] === PackageManager.PERMISSION_GRANTED
                val WRITE_EXTERNAL_STORAGE = grantResults[1] === PackageManager.PERMISSION_GRANTED
                if (READ_EXTERNAL_STORAGE && WRITE_EXTERNAL_STORAGE) {
                    // perform action when allow permission success
                } else {
                    Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }


    }

}
