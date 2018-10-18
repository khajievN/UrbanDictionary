package com.everything4droid.urbandickotlin.data.util

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.everything4droid.urbandickotlin.data.entity.WordDB
import com.everything4droid.urbandickotlin.data.response.Result
import com.everything4droid.urbandickotlin.data.response.Word
import com.everything4droid.urbandickotlin.ui.DefinitionActivity
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import kotlin.math.absoluteValue

/**
 * Created by Khajiev Nizomjon on 05/10/2018.
 */
suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> {
    return try {
        call()
    } catch (e: Exception) {
        when (e) {
            is IOException -> {
                Result.Error(ErrorKit(ERROR_STATUS.NETWORK))
            }
            is HttpException -> {
                Result.Error(ErrorKit(ERROR_STATUS.UNKNOWN))
//                when (e.code()) {
//                    HttpURLConnection.HTTP_BAD_REQUEST -> {
//                        Result.Error(ErrorKit(ERROR_STATUS.BAD_REQUEST))
//                    }
//                    HttpURLConnection.HTTP_INTERNAL_ERROR -> {
//                        Result.Error(ErrorKit(ERROR_STATUS.INTERNAL_SERVER))
//                    }
//                    HttpURLConnection.HTTP_NOT_FOUND -> {
//                        Result.Error(ErrorKit(ERROR_STATUS.NOT_FOUND))
//                    }
//                    else -> {
//                        Result.Error(ErrorKit(ERROR_STATUS.UNKNOWN))
//                    }
//                }
            }
            else -> {
                Result.Error(ErrorKit(ERROR_STATUS.DEV, e.message))
            }
        }
    }
}

val <T> T.exhaustive: T
    get() = this


fun <D : Any, T : LiveData<D>> T.observeWith(owner: LifecycleOwner, receiver: (D) -> Unit) {
    observe(owner, Observer<D> {
        if (it != null) {
            receiver(it)
        }
    })
}


enum class ERROR_STATUS {
    NETWORK,
    INTERNAL_SERVER,
    NOT_FOUND,
    BAD_REQUEST,
    UNKNOWN,
    DEV
}

fun Context.DefinitionDetailIntent(word: Word): Intent {
    return Intent(this, DefinitionActivity::class.java).apply {
        val wordJsonBody = Gson().toJson(word)
        putExtra(Const.INTENT_WORD_JSON, wordJsonBody)
    }
}

//
//fun WordDB.mapToWord(): Word {
//    return Word(
//            this.definition!!,
//            "",
//            this.thumbsUpNumber!!,
//            this.defId!!,
//            this.authorName!!,
//            this.word!!,
//            this.writtenOn!!,
//            null!!,
//            this.example!!,
//            this.thumbsDownNumber!!
//    )
//}
