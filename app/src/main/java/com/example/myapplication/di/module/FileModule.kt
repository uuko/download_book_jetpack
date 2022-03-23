package com.example.myapplication.di.module

import android.R
import android.content.Context
import android.os.Environment
import com.example.myapplication.util.Config
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Singleton


@Module
class FileModule {
    @Provides
    @Singleton
    fun providePath(): File {
        return File(
            Environment.getExternalStorageDirectory().absolutePath +
                    Config.FILE_DIR
        )
    }

//    @Provides
//    @Singleton
//    fun provideNotTransferFile(notTransferFile: NotTransferReceiveGoodFile?): NotTransferFileProvider<NotTransferReceiveGoodList?>? {
//        return notTransferFile
//    }
}