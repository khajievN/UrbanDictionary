package com.everything4droid.urbandickotlin.domain

import com.everything4droid.urbandickotlin.data.repository.UrbanRepository
import com.everything4droid.urbandickotlin.data.response.BaseMainResponse
import com.everything4droid.urbandickotlin.data.response.Result
import com.everything4droid.urbandickotlin.data.response.UserResponse
import com.everything4droid.urbandickotlin.data.service.body.LoginBody
import com.everything4droid.urbandickotlin.data.service.body.SignupBody

/**
 * Created by Khajiev Nizomjon on 14/10/2018.
 */
class SignupUseCase(private val urbanRepository: UrbanRepository) {
    suspend operator fun invoke(signupBody: SignupBody): Result<BaseMainResponse<UserResponse>> {
        return urbanRepository.sign(signupBody)
    }
}