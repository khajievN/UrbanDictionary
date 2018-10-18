package com.everything4droid.urbandickotlin.domain

import com.everything4droid.urbandickotlin.data.repository.UrbanRepository
import com.everything4droid.urbandickotlin.data.response.BaseResponse
import com.everything4droid.urbandickotlin.data.response.Result

/**
 * Created by Khajiev Nizomjon on 09/10/2018.
 */
class UrbanUseCase(private val urbanRepository: UrbanRepository) {
    suspend operator fun invoke(word: String): Result<BaseResponse> {
        return urbanRepository.definition(word)
    }
}