package com.everything4droid.urbandickotlin.jobs

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.everything4droid.urbandickotlin.data.CoroutinesContextProvider
import com.everything4droid.urbandickotlin.data.entity.WordDB
import com.everything4droid.urbandickotlin.data.repository.UrbanRepository
import com.everything4droid.urbandickotlin.data.response.BaseMainResponse
import com.everything4droid.urbandickotlin.data.response.Result
import com.everything4droid.urbandickotlin.data.response.WordResponse
import com.everything4droid.urbandickotlin.data.service.body.WordBody
import com.everything4droid.urbandickotlin.domain.UrbanLocalUseCase
import com.everything4droid.urbandickotlin.mvvm.LoginViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import org.koin.android.ext.android.inject
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by Khajiev Nizomjon on 15/10/2018.
 */
class AddFavoriteWordJob(
        context: Context,
        workerParameters: WorkerParameters) : Worker(context, workerParameters), KoinComponent {


    override fun doWork(): Result {

        val urbanRepository: UrbanRepository by inject()
        val coroutinesContextProvider: CoroutinesContextProvider by inject()
        val urbanLocalUseCase: UrbanLocalUseCase by inject()

        val wordBodyJson = inputData.getString("wordBody")
        val userId = inputData.getString("userId")

        val wordBody = Gson().fromJson<WordBody>(wordBodyJson, WordBody::class.java)

        launch(coroutinesContextProvider.io) {
            launchAddFavoriteWord(urbanRepository, urbanLocalUseCase,
                    userId!!.toInt(), wordBody)
        }

        return Result.SUCCESS
    }


    private suspend fun launchAddFavoriteWord(urbanRepository: UrbanRepository,
                                              urbanLocalUseCase: UrbanLocalUseCase,
                                              userId: Int, wordBody: WordBody) {
        val response = urbanRepository.addFavoriteWord(userId, wordBody)
        result = when (response) {
            is com.everything4droid.urbandickotlin.data.response.Result.Success -> {
                urbanLocalUseCase.updateSyncStatus(true,wordBody.defid)
                Result.SUCCESS
            }
            is com.everything4droid.urbandickotlin.data.response.Result.Error -> {
                urbanLocalUseCase.updateSyncStatus(false,wordBody.defid)
                Result.FAILURE
            }
        }
    }

}