package com.everything4droid.urbandickotlin.data.service.api

import com.everything4droid.urbandickotlin.data.response.BaseMainResponse
import com.everything4droid.urbandickotlin.data.response.UserResponse
import com.everything4droid.urbandickotlin.data.response.WordResponse
import com.everything4droid.urbandickotlin.data.service.body.LoginBody
import com.everything4droid.urbandickotlin.data.service.body.SignupBody
import com.everything4droid.urbandickotlin.data.service.body.WordBody
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Created by Khajiev Nizomjon on 14/10/2018.
 */
interface MainApi {

    @POST("api/v1/auth/")
    fun login(@Body loginBody: LoginBody): Deferred<BaseMainResponse<UserResponse>>

    @POST("api/v1/register/")
    fun signUp(@Body signupBody: SignupBody): Deferred<BaseMainResponse<UserResponse>>

    @PUT("api/v1/words/{userId}/")
    fun updateWord(@Path("userId") userId: Int, @Body word: WordBody): Deferred<BaseMainResponse<WordResponse>>

}