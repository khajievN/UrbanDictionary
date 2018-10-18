package com.everything4droid.urbandickotlin.data.service.api

import com.everything4droid.urbandickotlin.data.response.BaseResponse
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by Khajiev Nizomjon on 09/10/2018.
 */
interface Api {

    @Headers(
        "X-Mashape-Key:CvFx3R3btkmshlDgeWYXatMrdpNPp10YgPZjsnTlX4mv9GXtmi",
        "Content-type:application/json"
    )
    @GET("/define")
    fun getDefinetion(@Query("term") word: String): Deferred<BaseResponse>




}