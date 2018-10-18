package com.everything4droid.urbandickotlin.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.everything4droid.urbandickotlin.data.CoroutinesContextProvider
import com.everything4droid.urbandickotlin.data.datasource.SharedDataSource
import com.everything4droid.urbandickotlin.domain.LoginUseCase
import com.everything4droid.urbandickotlin.domain.SignupUseCase

/**
 * Created by Khajiev Nizomjon on 14/10/2018.
 */
class LoginViewModelFactory(private val loginUseCase: LoginUseCase,
                            private val signupUseCase: SignupUseCase,
                            private val sharedDataSource: SharedDataSource,
                            private val contextProvider: CoroutinesContextProvider) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(loginUseCase, signupUseCase, sharedDataSource, contextProvider) as T
    }

}