package com.everything4droid.urbandickotlin.data.response

/**
 * Created by Khajiev Nizomjon on 14/10/2018.
 */
data class BaseMainResponse<T>(val code: Int, val data: T, val message: String) {

    fun isSuccess(): Boolean {
        return 200 == code
    }

}