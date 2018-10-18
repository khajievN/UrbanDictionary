package com.everything4droid.urbandickotlin.data.datasource

import com.everything4droid.urbandickotlin.data.response.*
import com.everything4droid.urbandickotlin.data.service.api.Api
import com.everything4droid.urbandickotlin.data.service.api.MainApi
import com.everything4droid.urbandickotlin.data.service.body.LoginBody
import com.everything4droid.urbandickotlin.data.service.body.SignupBody
import com.everything4droid.urbandickotlin.data.service.body.WordBody
import com.everything4droid.urbandickotlin.data.util.ERROR_STATUS
import com.everything4droid.urbandickotlin.data.util.ErrorKit
import com.everything4droid.urbandickotlin.data.util.safeApiCall

/**
 * Created by Khajiev Nizomjon on 09/10/2018.
 */
class UrbanDataSource(private val api: Api, private val mainApi: MainApi) {


    // definition
    suspend fun definition(word: String) = safeApiCall(
            call = { requestDefinition(word) },
            errorMessage = "Error login"
    )

    private suspend fun requestDefinition(word: String): Result<BaseResponse> {
        api.getDefinetion(word).await().let {
            if (!it.isEmpty()) {
                return Result.Success(it)
            }
            return Result.Error(ErrorKit(ERROR_STATUS.DEV))
        }
    }

    // login
    suspend fun login(loginBody: LoginBody) = safeApiCall(
            call = { requestLogin(loginBody) },
            errorMessage = "Login Error"
    )

    private suspend fun requestLogin(loginBody: LoginBody): Result<BaseMainResponse<UserResponse>> {
        mainApi.login(loginBody).await().let {
            if (it.isSuccess()) {
                return Result.Success(it)
            }
            return Result.Error(ErrorKit(ERROR_STATUS.DEV, it.message))
        }
    }

    // signUp
    suspend fun signUp(signupBody: SignupBody) = safeApiCall(
            call = { requestSign(signupBody) },
            errorMessage = "Login Error"
    )

    private suspend fun requestSign(signupBody: SignupBody): Result<BaseMainResponse<UserResponse>> {
        mainApi.signUp(signupBody).await().let {
            if (it.isSuccess()) {
                return Result.Success(it)
            }
            return Result.Error(ErrorKit(ERROR_STATUS.DEV, it.message))
        }
    }

    //add as Favorite
    suspend fun addFavoriteWord(userId: Int, wordBody: WordBody) = safeApiCall(
            call = { requestAddFavoriteWord(userId, wordBody) },
            errorMessage = "Add word error "
    )

    private suspend fun requestAddFavoriteWord(userId: Int, wordBody: WordBody): Result<BaseMainResponse<WordResponse>> {
        mainApi.updateWord(userId, wordBody).await().let {
            if (it.isSuccess()) {
                return Result.Success(it)
            }
            return Result.Error(ErrorKit(ERROR_STATUS.DEV, it.message))
        }
    }


}