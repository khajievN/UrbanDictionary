package com.everything4droid.urbandickotlin.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.everything4droid.urbandickotlin.data.CoroutinesContextProvider
import com.everything4droid.urbandickotlin.data.datasource.SharedDataSource
import com.everything4droid.urbandickotlin.domain.UrbanLocalUseCase
import com.everything4droid.urbandickotlin.domain.UrbanUseCase

/**
 * Created by Khajiev Nizomjon on 09/10/2018.
 */
class UrbanViewModelFactory(private val urbanUseCase: UrbanUseCase,
                            private val urbanLocalUseCase: UrbanLocalUseCase,
                            private val sharedDataSource: SharedDataSource,
                            private val coroutinesContextProvider: CoroutinesContextProvider) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return com.everything4droid.urbandickotlin.mvvm.UrbanViewModel(urbanUseCase, urbanLocalUseCase,sharedDataSource, coroutinesContextProvider) as T
    }

}