package com.everything4droid.urbandickotlin.data.datasource

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Khajiev Nizomjon on 14/10/2018.
 */
class SharedDataSource(context: Context){

    private var preferences: SharedPreferences
    private val USER_ID = Pair("user_id", "")

    init {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return userId != ""
    }

    var userId: String
        get() = preferences.getString(USER_ID.first, USER_ID.second)!!
        set(value) = preferences.edit {
            it.putString(USER_ID.first, value)
        }

    companion object {
        private const val NAME = "UrbanKotlin"
        private const val MODE = Context.MODE_PRIVATE
    }

}