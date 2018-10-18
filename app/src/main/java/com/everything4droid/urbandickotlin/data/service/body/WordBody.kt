package com.everything4droid.urbandickotlin.data.service.body

/**
 * Created by Khajiev Nizomjon on 15/10/2018.
 */
class WordBody(
        val definition: String,
        val thumbs_up: Int,
        val author: String,
        val word: String,
        val defid: Int,
        val written_on: String,
        val example: String,
        val thumbs_down: Int,
        val userId: Int,
        val isFavorite: Boolean
)