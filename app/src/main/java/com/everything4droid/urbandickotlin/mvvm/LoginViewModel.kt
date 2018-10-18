package com.everything4droid.urbandickotlin.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.everything4droid.urbandickotlin.data.CoroutinesContextProvider
import com.everything4droid.urbandickotlin.data.datasource.SharedDataSource
import com.everything4droid.urbandickotlin.data.response.BaseMainResponse
import com.everything4droid.urbandickotlin.data.response.Result
import com.everything4droid.urbandickotlin.data.response.UserResponse
import com.everything4droid.urbandickotlin.data.service.body.LoginBody
import com.everything4droid.urbandickotlin.data.service.body.SignupBody
import com.everything4droid.urbandickotlin.data.util.ErrorKit
import com.everything4droid.urbandickotlin.domain.LoginUseCase
import com.everything4droid.urbandickotlin.domain.SignupUseCase
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

/**
 * Created by Khajiev Nizomjon on 14/10/2018.
 */
class LoginViewModel(private val loginUseCase: LoginUseCase,
                     private val signupUseCase: SignupUseCase,
                     private val sharedDataSource: SharedDataSource,
                     private val contextProvider: CoroutinesContextProvider) : BaseViewModel() {


    private val _uiModel = MutableLiveData<UserResponse>()
    val uiModel: LiveData<UserResponse>
        get() = _uiModel


    fun login(email: String, password: String) {
        progress.postValue(true)
        val loginBody = LoginBody(email, password)
        launchLogin(loginBody)
    }

    private fun launchLogin(loginBody: LoginBody) = launch(contextProvider.io) {
        val result = loginUseCase(loginBody)
        withContext(contextProvider.io) { onResult(result) }
    }


    fun sign(email: String, password: String, username: String) {
        progress.postValue(true)
        val signupBody = SignupBody(email, password, username)
        launchSign(signupBody)
    }

    private fun launchSign(signupBody: SignupBody) = launch(contextProvider.io) {
        val result = signupUseCase(signupBody)
        withContext(contextProvider.io) { onResult(result) }
    }

    private fun onResult(result: Result<BaseMainResponse<UserResponse>>) {
        progress.postValue(false)
        when (result) {
            is Result.Success -> {
                emitUiModel(result.data.data)
            }
            is Result.Error -> {
                errorBase.postValue(result.exception as ErrorKit?)
            }
        }
    }

    private fun emitUiModel(data: UserResponse) =
            launch(contextProvider.main) {
                sharedDataSource.userId = data.id
                _uiModel.value = data
            }

}