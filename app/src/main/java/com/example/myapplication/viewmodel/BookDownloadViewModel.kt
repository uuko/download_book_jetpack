package com.example.myapplication.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Artical
import com.example.myapplication.model.BookAllSize
import com.example.myapplication.model.ProgressData
import com.example.myapplication.repository.BookDownloadRepository
import javax.inject.Inject

class BookDownloadViewModel @Inject constructor(private val dataModel: BookDownloadRepository) : ViewModel() {


    val articalData: MutableLiveData<Artical> = dataModel.artical
    val progress: MutableLiveData<ProgressData> = dataModel.progress
    val bookAllSize: MutableLiveData<BookAllSize> = dataModel.bookAllSize

    fun parseCzBooksAndSave(url: String = "",folder:String="book") {
        Log.e("onCreate", "getAritcal: ")
        dataModel.parseCzBooksAndSave(url)
    }

    fun parseNovelAllBooksAndSave(url: String = "",folder:String="book") {
        Log.e("onCreate", "VM == parseNovelAllBooksAndSave: ")
        dataModel.parseNovelAllBooksAndSave(url = url)
    }

    fun parseNovelOneBookAndSave(wh: List<String>,folder:String="book") {
        Log.e("onCreate", "VM == parseNovelOneBookAndSave: ")
        dataModel.parseNovelOneBookAndSave(wh)
    }


    fun parseLofterAndSave(url: String = "",context: Context,folder:String="lofter"){
        dataModel.parseLofterAndSave(url,context)
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