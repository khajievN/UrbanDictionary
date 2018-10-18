package com.everything4droid.urbandickotlin.mvvm

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.everything4droid.urbandickotlin.data.util.ErrorKit

/**
 * Created by Khajiev Nizomjon on 15/09/2018.
 */
open class BaseViewModel : ViewModel() {

    val errorBase = MutableLiveData<ErrorKit>()
    val progress = MutableLiveData<Boolean>()


    fun progress(): MutableLiveData<Boolean> {
        return progress
    }

    override fun onCleared() {
        super.onCleared()

    }



}