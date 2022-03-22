package com.example.myapplication.model

data class Artical(var title: String="",
                   var content: String="",
                   var author: String="",
                   var url:String="",
                   var chapter:String="",
                   var imgUrl:List<String>) {

    override fun toString(): String {
        var imgUrlString="";
        for (element in imgUrl){
            imgUrlString= "$imgUrlString   ,$element"
        }
        return "Article{" +
                "title='" + title + '\''.toString() +
                ", content='" + content + '\''.toString() +
                ", author='" + author + '\''.toString() +
                ", url='" + url + '\''.toString() +
                ", imgUrl='" + imgUrlString + '\''.toString() +
                '}'.toString();
    }
}