package com.everything4droid.urbandickotlin.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Khajiev Nizomjon on 10/10/2018.
 */

@Entity(
        tableName = "word"
)
class WordDB {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    var definition: String? = null

    var thumbsUpNumber: Int? = null

    var defId: Int? = null

    var authorName: String? = null

    var word: String? = null

    var writtenOn: String? = null

    var example: String? = null

    var thumbsDownNumber: Int? = null

    var isFavorite: Boolean? = false

    var isSynced: Boolean? = false
}