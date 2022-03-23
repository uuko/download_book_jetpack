package com.example.myapplication.di.component

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.App
import com.example.myapplication.api.ApiService
import com.example.myapplication.di.module.*
import com.example.myapplication.mgr.CzBookParser
import com.example.myapplication.mgr.LofterParser
import com.example.myapplication.mgr.PermissionMgr
import com.example.myapplication.util.LoginPreferencesProvider
import com.example.myapplication.util.SchedulerProvider
import com.example.myapplication.viewmodel.BookDownloadViewModel
import com.example.myapplication.viewmodel.ViewModelFactory
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        FileModule::class,
        MgrModule::class,
        NetworkModule::class,
        ViewModelModule::class

    ]
)
interface ApplicationComponent  {
    fun inject(application: Application)

    fun getAPI(): ApiService

    fun getSchedulerProvider(): SchedulerProvider

    fun getObservable(): Observable<Any>

    fun getCompositeDisposable(): CompositeDisposable

    //    SharePreferences
    fun getLoginPreferencesProvider(): LoginPreferencesProvider

    fun getCzBookParser(): CzBookParser

    fun getLofterParser(): LofterParser

    fun getPermissionMgr(): PermissionMgr

    fun getViewModelFactory(): ViewModelProvider.Factory

}
