package com.example.myapplication.repository

import android.content.Context
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.mgr.CzBookParser
import com.example.myapplication.mgr.LofterParser
import com.example.myapplication.mgr.PdfItextUtil
import com.example.myapplication.model.Artical
import com.example.myapplication.model.BookAllSize
import com.example.myapplication.model.ProgressData
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class BookDownloadRepository {

    private var _artical = MutableLiveData(Artical("", "", "", "", "", listOf()))
    val artical: MutableLiveData<Artical> = _artical


    private var _bookAllSize = MutableLiveData(BookAllSize(listOf()))
    val bookAllSize: MutableLiveData<BookAllSize> = _bookAllSize

    private var _progress = MutableLiveData(ProgressData())
    val progress: MutableLiveData<ProgressData> = _progress

    fun parseLofterAndSave(url: String, context: Context) {
        Log.e("onCreate", "parseLofterAndSave: $url" )
        LofterParser().getLofterParserData(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Artical> {
                override fun onSuccess(t: Artical) {
                    Log.d("onCreate", "parseLofterAndSave$t")
                    _artical.postValue(t)
//                    saveToWordRx(t, context)
                    saveToFileRx(t)

                    _progress.postValue(
                        ProgressData(
                            text = "完成",
                            show = false
                        )
                    )


                }

                override fun onSubscribe(d: Disposable) {
                    Log.d("onCreate", "parseLofterAndSave onSubscribe")
                    _progress.postValue(
                        ProgressData(
                            text = "parseLofterAndSave 下載中",
                            show = true
                        )
                    )
                }

                override fun onError(e: Throwable) {
                    Log.d("onCreate", "parseLofterAndSave onError$e")
                    _progress.postValue(
                        ProgressData(
                            text = "parseLofterAndSave 下載失敗",
                            show = true
                        )
                    )
                }


            })

    }

    fun parseCzBooksAndSave(url: String = "") {
        Log.e("onCreate", "parseCzBooksAndSave: $url")
        CzBookParser().getCzBooksParserData(url = url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Artical> {
                override fun onSuccess(t: Artical) {
                    Log.d("onCreate parseCzBooks", "onSuccess$t")
                    _artical.postValue(t)
                    _progress.postValue(
                        ProgressData(
                            text = "完成",
                            show = false
                        )
                    )
                    saveToFileRx(t)
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d("onCreate parseCzBook", "onSubscribe")
                    _progress.postValue(
                        ProgressData(
                            text = "下載中",
                            show = true
                        )
                    )
                }

                override fun onError(e: Throwable) {
                    Log.d("onCreate parseCzBook", "onError$e")
                    _progress.postValue(
                        ProgressData(
                            text = "失敗 $e",
                            show = false
                        )
                    )
                }
            })
    }

    fun parseNovelAllBooksAndSave(url: String) {
        CzBookParser().getCzBookAllBookData(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<String>> {
                override fun onSuccess(wh: List<String>) {
                    Log.d("onCreate AllBksAndSave", "onSuccess$wh")
                    bookAllSize.postValue(BookAllSize(wh))
//                    parseNovelOneBookAndSave(wh, 0)
                    _progress.postValue(
                        ProgressData(
                            text = "完成",
                            show = false
                        )
                    )

                }

                override fun onSubscribe(d: Disposable) {
                    Log.d("onCreate AllBksAndSave", "onSubscribe")
//                    setLoadText("載入中", true)
//                    showProgressBar()
                    _progress.postValue(
                        ProgressData(
                            text = "載入中 parseNovelAllBooksAndSave",
                            show = true
                        )
                    )
                }

                override fun onError(e: Throwable) {
                    Log.d("onError", "onError$e")
//                    text.text.clear()
//                    setLoadText("載入失敗", true)
//                    dismissProgressBar()
//                    setLoadText("載入失敗", false)
                    _progress.postValue(
                        ProgressData(
                            text = "失敗 $e",
                            show = false
                        )
                    )
                }


            })
    }


    fun parseNovelOneBookAndSave(wh: List<String>, nowInt: Int = 0) {
        var finalInt = nowInt;
        var isEnd = false
        CzBookParser().getCzBookAllBookParseData(wh, finalInt, isEnd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Artical> {
                override fun onSuccess(ar: Artical) {
                    Log.d("onCreate", "onSuccess OneBookAndSave $wh")
                    _artical.postValue(ar)
                    when {
                        finalInt < wh.size - 2 -> {
                            finalInt++
                            _progress.postValue(
                                ProgressData(
                                    text = "下載第  $finalInt 章中",
                                    show = true
                                )
                            )
                            isEnd = false
                            parseNovelOneBookAndSave(wh, finalInt)
                        }
                        finalInt == wh.size - 2 -> {
                            finalInt++
                            _progress.postValue(
                                ProgressData(
                                    text = "下載第  $finalInt 章中",
                                    show = true
                                )
                            )
                            isEnd = true
                            parseNovelOneBookAndSave(wh, finalInt)
                        }
                        else -> {
                            finalInt = 0
                            saveToFileRx(ar)
                        }
                    }


                }

                override fun onSubscribe(d: Disposable) {
                    Log.d("onCreate", "OneBookAndSave onSubscribe")
                    _progress.postValue(
                        ProgressData(
                            text = "載入中 parseNovelAllBooksAndSave",
                            show = true
                        )
                    )
                }

                override fun onError(e: Throwable) {
                    Log.d("onCreate", "OneBookAndSave onError$e")
                    _progress.postValue(
                        ProgressData(
                            text = "失敗 $e",
                            show = false
                        )
                    )
                    finalInt = 0
                }


            })
    }


    fun saveToFileRx(t: Artical) {
        saveToFile(t)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d("onCreate", " saveToFileRx onSubscribe  saveToFile")
                    progress.postValue(ProgressData(text = "儲存檔案中..", show = true))
                }

                override fun onError(e: Throwable) {
                    Log.d("onCreate", "saveToFileRx onError  saveToFile$e")
                    progress.postValue(ProgressData(text = "儲存失敗", show = false))
                }

                override fun onComplete() {
                    Log.d("onCreate", "saveToFileRx onComplete  saveToFile")
                    progress.postValue(ProgressData(text = "儲存成功", show = false))
                }
            })
    }

    //   saveToFile(ar)
//                                .subscribeOn(Schedulers.io())
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe(object : CompletableObserver {
//                                    override fun onSubscribe(d: Disposable) {
//                                        Log.d("onSubscribe", "onSubscribe  saveToFile")
//                                        setLoadText("儲存中", true)
//                                    }
//
//                                    override fun onError(e: Throwable) {
//                                        Log.d("onError", "onError  saveToFile" + e)
//                                        setLoadText("儲存失敗", true)
//                                        dismissProgressBar()
//                                    }
//
//                                    override fun onComplete() {
//                                        Log.d("onComplete", "onComplete  saveToFile")
//                                        setLoadText("儲存中", false)
//                                        dismissProgressBar()
//                                    }
//
//                                })
    fun saveToFile(t: Artical): Completable {
        val name = t.title + ".txt"
//        val filePath = "/storage/emulated/0/DCIM"
        val filePath: String =
            (Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).path) + "/books"
        val dir = File(
            Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).path
                    + "/books"
        );
        return Completable.create {
            run {

                if (!dir.exists()) {
                    dir.mkdir();
                }
                if (name.isNotEmpty()) {
                    try {
                        val outFile = File(filePath, name)
                        val outputStream = FileOutputStream(outFile)
                        outputStream.write(t.content.toByteArray())
                        it.onComplete()
                    } catch (e: IOException) {
                        e.printStackTrace()
                        it.onError(e)
                    }
                }
            }
        }
    }


    private fun saveToWordRx(t: Artical, context: Context) {
        saveToWord(t, context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d("onCreate", "saveToWordRx  onSubscribe")
                    _progress.postValue(
                        ProgressData(
                            text = "儲存中",
                            show = true
                        )
                    )
                }

                override fun onError(e: Throwable) {
                    Log.d("onCreate", "onError  saveToWordRx$e")
                    _progress.postValue(
                        ProgressData(
                            text = "儲存失敗 $e",
                            show = false
                        )
                    )
                }

                override fun onComplete() {
                    Log.d("onCreate", "onComplete  saveToWordRx")
                    _progress.postValue(
                        ProgressData(
                            text = "儲存成功",
                            show = false
                        )
                    )
                }

            })
    }

    private fun saveToWord(t: Artical, context: Context): Completable {
        return Completable.create {
            run {

                var pdfItextUtil: PdfItextUtil? = null
                try {
                    val filePath: String =
                        (Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).path) + "/books/"

                    Log.e("onCreate", "saveToWord: $t", )
                    pdfItextUtil =
                        PdfItextUtil(context, filePath + t.title + ".pdf")
                            .addTitleToPdf(t.title)
                            .addTextToPdf(t.author)
                            .addTextToPdf(t.content)
//                        .addImageToPdfCenterH(t.imgUrl[0], 160f, 160f)
                    for (i in 0 until t.imgUrl.size) {
                        pdfItextUtil?.addImageToPdfCenterH(t.imgUrl[i]);
                    }
//                        .addImageToPdfCenterH(getImageFilePath(), 160f, 160f)
//                        .addTextToPdf(getTvString(tv_content))
                } catch (e: IOException) {
                    e.printStackTrace()
                    it.onError(e)
                } finally {
                    pdfItextUtil?.close()
                    it.onComplete()
                }

            }
        }

    }

//    saveToFile(t)
//    .subscribeOn(Schedulers.io())
//    .observeOn(AndroidSchedulers.mainThread())
//    .subscribe(object : CompletableObserver {
//        override fun onSubscribe(d: Disposable) {
//            Log.d("onSubscribe", "onSubscribe  saveToFile")
//            setLoadText("儲存中", true)
//        }
//
//        override fun onError(e: Throwable) {
//            Log.d("onError", "onError  saveToFile" + e)
//            setLoadText("儲存失敗", true)
//            dismissProgressBar()
//        }
//
//        override fun onComplete() {
//            Log.d("onComplete", "onComplete  saveToFile")
//            setLoadText("儲存中", false)
//            dismissProgressBar()
//        }
//
//    })
}