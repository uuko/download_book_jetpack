package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.component.ApplicationComponent
import com.example.myapplication.di.module.ApplicationModule
import dagger.android.DaggerApplication


class App : Application(){
//    private var mApplicationComponent: ApplicationComponent = null


    override fun onCreate() {
        super.onCreate()

//        mApplicationComponent = DaggerApplicationComponent.builder()
//            .applicationModule(ApplicationModule(this))
//            .aPIModule(APIModule())
//            .printerModule(PrinterModule())
//            .fTPModule(FTPModule())
//            .socketModule(SocketModule())
//            .notTransferModule(NotTransferModule())
//            .build()
//        mApplicationComponent!!.inject(this)
    }

//    fun getApplicationComponent(): ApplicationComponent {
//        return mApplicationComponent
//    }

}