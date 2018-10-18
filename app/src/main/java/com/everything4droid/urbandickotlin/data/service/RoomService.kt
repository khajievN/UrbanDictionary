package com.everything4droid.urbandickotlin.data.service

import androidx.room.Database
import androidx.room.RoomDatabase
import com.everything4droid.urbandickotlin.data.entity.WordDB
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration



/**
 * Created by Khajiev Nizomjon on 10/10/2018.
 */

@Database(entities = [WordDB::class], version = 3)
abstract class RoomService : RoomDatabase() {

    abstract fun getWordDao(): WordDAO
}