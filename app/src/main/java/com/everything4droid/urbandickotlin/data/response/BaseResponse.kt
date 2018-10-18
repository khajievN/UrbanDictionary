package com.everything4droid.urbandickotlin.data.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Khajiev Nizomjon on 09/10/2018.
 */
data class BaseResponse(@SerializedName("list") val wordList: List<Word>) {

    fun isEmpty() = wordList.isEmpty()
}


data class Word(
        @SerializedName("definition") val definition: String,
        @SerializedName("permalink") val permaLink: String,
        @SerializedName("thumbs_up") val thumbsUpNumber: Int,
        @SerializedName("defid") val defId: Int,
        @SerializedName("author") val authorName: String,
        @SerializedName("word") val word: String,
        @SerializedName("written_on") val writtenOn: String,
        @SerializedName("sound_urls") val soundList: List<String>,
        @SerializedName("example") val example: String,
        @SerializedName("thumbs_down") val thumbsDownNumber: Int
)


data class Sound(val soundName: String, val soundUrl: String, val isPlaying: Boolean)

