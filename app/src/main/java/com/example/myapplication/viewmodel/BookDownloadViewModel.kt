package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Artical
import com.example.myapplication.model.BookAllSize
import com.example.myapplication.model.ProgressData
import com.example.myapplication.repository.BookDownloadRepository
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BookDownloadViewModel(private val dataModel: BookDownloadRepository) : ViewModel() {


    val articalData: MutableLiveData<Artical> = dataModel.artical
    val progress: MutableLiveData<ProgressData> = dataModel.progress
    val bookAllSize: MutableLiveData<BookAllSize> = dataModel.bookAllSize

    fun parseCzBooksAndSave(url: String = "") {
        Log.e("onCreate", "getAritcal: ")
        dataModel.parseCzBooksAndSave(url)
    }

    fun parseNovelAllBooksAndSave(url: String = "") {
        Log.e("onCreate", "VM == parseNovelAllBooksAndSave: ")
        dataModel.parseNovelAllBooksAndSave(url = url)
    }

    fun parseNovelOneBookAndSave(wh: List<String>) {
        Log.e("onCreate", "VM == parseNovelOneBookAndSave: ")
        dataModel.parseNovelOneBookAndSave(wh)
    }



//    private var networkState: LiveData<NetworkState>? = null
//    private var articleLiveData: LiveData<PagedList<Response>>? = null
//    lateinit var feedDataFactory: FeedDataFactory
//    fun init() {
//        val executor = Executors.newFixedThreadPool(5)
//        feedDataFactory = FeedDataFactory()
//        networkState = Transformations.switchMap(
//            feedDataFactory.getMutableLiveData()
//        ) { dataSource -> dataSource.networkState }
//        val pagedListConfig: PagedList.Config = PagedList.Config.Builder()
//            .setEnablePlaceholders(false)
//            .setInitialLoadSizeHint(10)
//            .setPageSize(15).build()
//        articleLiveData = LivePagedListBuilder(feedDataFactory, pagedListConfig)
//            .setFetchExecutor(executor)
//            .build()
//    }
//
//    fun getNetworkState(): LiveData<NetworkState>? {
//        return networkState
//    }
//
//    fun getArticleLiveData(): LiveData<PagedList<Response>>? {
//        return articleLiveData
//    }
//
//    fun invalidate() {
//        feedDataFactory.invalidate()
//    }
//
//    fun deleteArticle(request: List<DeleteArticleRequest>): MutableLiveData<UnitResponse> {
//        return dataModel.deleteArticle(request)
//    }
//
//    init {
//        init()
//    }
}