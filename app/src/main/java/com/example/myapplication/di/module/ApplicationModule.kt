package com.example.myapplication.di.module

import android.app.Application
import android.content.Context
import com.example.myapplication.App
import com.example.myapplication.util.LoginPreferences
import com.example.myapplication.util.LoginPreferencesProvider
import com.example.myapplication.util.SchedulerProvider
import com.example.myapplication.util.SchedulerProviderImp
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton


@Module
class ApplicationModule(app: App) {
    private var application: Application = app


    @Provides
    fun provideContext(): Context {
        return application.applicationContext
    }

    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Provides
    fun provideSchedulerProvider(schedulerProviderImp: SchedulerProviderImp): SchedulerProvider {
        return schedulerProviderImp
    }

    @Provides
    fun provideUIObservable(schedulerProvider: SchedulerProvider): Observable<Any> {
        return Observable.create(ObservableOnSubscribe<Any> { e -> e.onComplete() })
            .subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    @Singleton
    fun provideLoginPreferencesProvider(loginPreferences: LoginPreferences): LoginPreferencesProvider {
        return loginPreferences
    }
}