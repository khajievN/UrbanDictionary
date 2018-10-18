package com.everything4droid.urbandickotlin.data.repository

import com.everything4droid.urbandickotlin.data.datasource.UrbanDataSource
import com.everything4droid.urbandickotlin.data.response.*
import com.everything4droid.urbandickotlin.data.service.body.LoginBody
import com.everything4droid.urbandickotlin.data.service.body.SignupBody
import com.everything4droid.urbandickotlin.data.service.body.WordBody

/**
 * Created by Khajiev Nizomjon on 09/10/2018.
 */
class UrbanRepository(private val urbanDataSource: UrbanDataSource) {


    suspend fun definition(word: String): Result<BaseResponse> {
        return urbanDataSource.definition(word)
    }


    suspend fun login(loginBody: LoginBody): Result<BaseMainResponse<UserResponse>> {
        return urbanDataSource.login(loginBody)
    }

    suspend fun sign(signupBody: SignupBody): Result<BaseMainResponse<UserResponse>> {
        return urbanDataSource.signUp(signupBody)
    }

    suspend fun addFavoriteWord(userId: Int, wordBody: WordBody): Result<BaseMainResponse<WordResponse>> {
        return urbanDataSource.addFavoriteWord(userId, wordBody)
    }
}