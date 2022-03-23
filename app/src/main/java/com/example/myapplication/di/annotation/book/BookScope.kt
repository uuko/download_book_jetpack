package com.example.myapplication.di.annotation.book

import java.lang.annotation.Documented
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

@Documented
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class BookScope