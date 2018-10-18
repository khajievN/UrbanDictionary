package com.everything4droid.urbandickotlin.domain

import com.everything4droid.urbandickotlin.data.repository.UrbanRepository
import com.everything4droid.urbandickotlin.data.response.BaseMainResponse
import com.everything4droid.urbandickotlin.data.response.Result
import com.everything4droid.urbandickotlin.data.response.UserResponse
import com.everything4droid.urbandickotlin.data.service.body.LoginBody

/**
 * Created by Khajiev Nizomjon on 14/10/2018.
 */
class LoginUseCase(private val urbanRepository: UrbanRepository) {
    suspend operator fun invoke(loginBody: LoginBody): Result<BaseMainResponse<UserResponse>> {
        return urbanRepository.login(loginBody)
    }
}