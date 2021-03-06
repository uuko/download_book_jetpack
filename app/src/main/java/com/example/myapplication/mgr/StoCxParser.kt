package com.example.myapplication.mgr

import android.util.Log
import com.example.myapplication.model.Artical
import io.reactivex.Single
import org.jsoup.Jsoup
import java.lang.Exception

class StoCxParser {
    fun getStoCxParserData(url :String,cookie: String) : Single<Artical> {
        return Single.create<Artical> {

            try {
                val doc= Jsoup.connect(url)
                    .cookie("Cookie",cookie)
                    .get()
                val titledoc=doc.select("title")
                val content_doc=doc.select("#BookContent")
                var title=titledoc.text()
                Log.d("content",content_doc.text())
                var author=doc.select("#bookbox").text()
                val  content=content_doc.text()
                val imgUrlList= arrayListOf<String>()
//                 artical.value=Artical(title,content,author,url,imgUrlList)
                it.onSuccess(Artical(title,content,author,url,"",imgUrlList))
            }
            catch (e : Exception){
                it.onError(e)
            }

        }


    }
    fun getStoBookAllBookData(url: String,cookie: String) : Single<List<String>>{
        return Single.create<List<String>> {

            try {
                val doc= Jsoup.connect(url)
                    .cookie("Cookie",cookie)
                    .get()
                val liUI=doc.select("#webPage")
                val allPageA=liUI.select("a")
                val storUrl=url
                val urlCotact=storUrl.split("-")[2]
                val urlu=url.substring(0,url.length-urlCotact.length)
                val allPageSize=liUI.select("a")[allPageA.size-1].attr("href").split("-")[2].split(".html")[0].toInt()

                val imgUrlList= arrayListOf<String>()
                for (i in 1 .. allPageSize){
                    imgUrlList.add(urlu+i+".html")
                }
//                 artical.value=Artical(title,content,author,url,imgUrlList)

                it.onSuccess(imgUrlList)
            }
            catch (e : Exception){
                it.onError(e)
            }

        }
    }
    var  allContent=""
    fun getStBookAllBookParseData(url: List<String>,nowInt: Int,cookie:String) : Single<Artical>{
        return Single.create<Artical> {

            try {


                val doc= Jsoup.connect(url[nowInt])
                    .cookie("Cookie",cookie)
                    .get()
                val titledoc=doc.select("title")
                val content_doc=doc.select("#BookContent")
                var title=titledoc.text()
                Log.d("content",content_doc.text())
                var author=doc.select("#bookbox").text()
                val  content=content_doc.text()
                allContent += "???"+nowInt.toString()+"???End..."+"\n"+content.replace("  ", "\n")
                    .replace(" ", "\n")
//                    val author_list=doc.select("div.name")
//                    var author=author_list.text()
                val imgUrlList= arrayListOf<String>()
                it.onSuccess(Artical(title,allContent,title,url[nowInt],"",imgUrlList))






//                 artical.value=Artical(title,content,author,url,imgUrlList)


            }
            catch (e : Exception){
                Log.d("testsssssssss",""+e)
                it.onError(e)
            }

        }
    }
}