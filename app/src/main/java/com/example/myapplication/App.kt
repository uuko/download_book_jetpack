package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.component.ApplicationComponent
import com.example.myapplication.di.component.DaggerApplicationComponent
import com.example.myapplication.di.module.ApplicationModule
import com.example.myapplication.di.module.MgrModule
import dagger.android.DaggerApplication


class App : Application(){
   private lateinit var mApplicationComponent:ApplicationComponent


    override fun onCreate() {
        super.onCreate()

        mApplicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .mgrModule(MgrModule())
            .build()
        mApplicationComponent!!.inject(this)
    }

    fun getApplicationComponent(): ApplicationComponent {
        return mApplicationComponent
    }

}