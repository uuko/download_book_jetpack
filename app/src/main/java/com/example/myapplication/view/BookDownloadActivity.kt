package com.example.myapplication.view

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.os.IBinder
import android.provider.Settings
import android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
import android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.di.component.book.BookComponent
import com.example.myapplication.di.component.book.DaggerBookComponent
import com.example.myapplication.di.module.book.BookModule
import com.example.myapplication.mgr.CzBookParser
import com.example.myapplication.mgr.PermissionMgr
import com.example.myapplication.service.FloatingButtonService
import com.example.myapplication.viewmodel.BookDownloadViewModel
import kotlinx.android.synthetic.main.activity_book_download.*
import javax.inject.Inject


class BookDownloadActivity : AppCompatActivity() {
    //    private val factory = BookDownloadViewModelFactory(BookDownloadRepository())
//    viewmodel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: BookDownloadViewModel by viewModels {
        viewModelFactory
    }


    //    permissionMgr

    var permissionMgr: PermissionMgr? = null
        @Inject set

    private var hasBind = false
    lateinit var mBookComponent: BookComponent

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_download)
        initView()
        initPermission()

        viewModel.bookAllSize.observe(this) {

            if (folderText.text.toString().isNotEmpty()) viewModel.parseNovelOneBookAndSave(
                it.list,
                folderText.text.toString()
            )
            else viewModel.parseNovelOneBookAndSave(it.list)
        }
        viewModel.progress.observe(this) {
            loadText.visibility = View.VISIBLE
            if (it.show) {
                progress_circular.visibility = View.VISIBLE


            } else {
                progress_circular.visibility = View.GONE
//                loadText.visibility = View.GONE
            }

            Log.e("onCreate", " viewModel.progress.observe:  ${it.text}")
            loadText.text = it.text
        }


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initView() {
        mBookComponent = DaggerBookComponent.builder()
            .bookModule(BookModule(this))
            .applicationComponent((application as App).getApplicationComponent())
            .build()
        mBookComponent.inject(this)
        submit.setOnClickListener {
            val url = inputText.text.toString()
            if (url.isNotEmpty()) {
                if (url.split("https://")[1].contains("czbooks.net")) {
                    if (folderText.text.toString().isNotEmpty()) viewModel.parseCzBooksAndSave(
                        url,
                        folderText.text.toString()
                    )
                    else viewModel.parseCzBooksAndSave(url)
                }
                //else if (url.split("https://")[1].contains("www.sto.cx")) {
//                    var mUrl = url.replace("mbook", "book")
//                    parseStoAndSave(url)
//                }
                //
                else {
                    if (folderText.text.toString().isNotEmpty()) viewModel.parseLofterAndSave(
                        url = url,
                        context = this,
                        folder = folderText.text.toString()
                    )
                    else viewModel.parseLofterAndSave(url, this)
                }
            }
        }
        submitAll.setOnClickListener {
            val url = inputText.text.toString()
            if (url.isNotEmpty()) {

                if (url.split("https://")[1].contains("czbooks.net")) {
                    if (folderText.text.toString()
                            .isNotEmpty()
                    ) viewModel.parseNovelAllBooksAndSave(url, folder = folderText.text.toString())
                    else viewModel.parseNovelAllBooksAndSave(url)
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
        folderText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                location.text = "download/$p0"
            }

            override fun afterTextChanged(p0: Editable?) {

            }


        })
        floatBtn.setOnClickListener { startFloatingButtonService() }
    }

    private fun initPermission() {
        if (!permissionMgr?.checkPermission(this)!!) {
            permissionMgr?.requestPermission(this)
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
        } else if (requestCode == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show()
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
                val READ_EXTERNAL_STORAGE =
                    grantResults[0] === PackageManager.PERMISSION_GRANTED
                val WRITE_EXTERNAL_STORAGE =
                    grantResults[1] === PackageManager.PERMISSION_GRANTED
                if (READ_EXTERNAL_STORAGE && WRITE_EXTERNAL_STORAGE) {
                    // perform action when allow permission success
                } else {
                    Toast.makeText(
                        this,
                        "Allow permission for storage access!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun startFloatingButtonService() {
        val floatingButtonService = FloatingButtonService()
        if (floatingButtonService.getStart()) {
            Log.e("onCreate", "floatingButtonService.getStart: ")
            return
        }
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "無權限", Toast.LENGTH_SHORT)
            startActivityForResult(
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                ), 0
            )
        } else {
            Log.e("onCreate", "floatingButtonService.bindService: ")
            val intent = Intent(this, FloatingButtonService::class.java)
            hasBind = bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    private var mServiceConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as FloatingButtonService.MyBinder
            Log.e("onCreate", "floatingButtonService.onServiceConnected: ")
            binder.getServces()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.e("onCreate", "floatingButtonService.onServiceDisconnected: ")
        }
    }

}
