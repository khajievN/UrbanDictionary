package com.everything4droid.urbandickotlin.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.everything4droid.urbandickotlin.data.CoroutinesContextProvider
import com.everything4droid.urbandickotlin.data.datasource.SharedDataSource
import com.everything4droid.urbandickotlin.data.entity.WordDB
import com.everything4droid.urbandickotlin.data.response.BaseResponse
import com.everything4droid.urbandickotlin.data.response.Result
import com.everything4droid.urbandickotlin.data.response.Word
import com.everything4droid.urbandickotlin.data.service.body.WordBody
import com.everything4droid.urbandickotlin.data.util.Const
import com.everything4droid.urbandickotlin.data.util.ErrorKit
import com.everything4droid.urbandickotlin.data.util.observeWith
import com.everything4droid.urbandickotlin.domain.UrbanLocalUseCase
import com.everything4droid.urbandickotlin.domain.UrbanUseCase
import com.everything4droid.urbandickotlin.jobs.AddFavoriteWordJob
import com.google.gson.Gson
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

/**
 * Created by Khajiev Nizomjon on 09/10/2018.
 */
class UrbanViewModel(private val urbanUseCase: UrbanUseCase,
                     private val urbanLocalUseCase: UrbanLocalUseCase,
                     private val sharedDataSource: SharedDataSource,
                     private val coroutinesContextProvider: CoroutinesContextProvider) : BaseViewModel() {


    private var currentJob = Job()

    lateinit var workManager: WorkManager

    private val _uiModel = MutableLiveData<List<Word>>()
    val uiModel: LiveData<List<Word>>
        get() = _uiModel

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    private val _isSynced = MutableLiveData<Boolean>()
    val isSynced: LiveData<Boolean>
        get() = _isSynced

    private val _allFavoriteWords = MutableLiveData<List<Word>>()
    val allFavoriteWords: LiveData<List<Word>>
        get() = _allFavoriteWords

    private val _isJobDone = MutableLiveData<List<WorkStatus>>()
    val isJobDone: LiveData<List<WorkStatus>>
        get() = _isJobDone


    init {
        workManager = WorkManager.getInstance()
    }

    fun getDefinition(word: String) {
        progress.postValue(true)
        launchDefinition(word)
    }


    fun insertOrUpdateWord(word: Word) {

        val wordDB = WordDB()
        wordDB.authorName = word.authorName
        wordDB.defId = word.defId
        wordDB.definition = word.definition
        wordDB.example = word.example
        wordDB.thumbsDownNumber = word.thumbsDownNumber
        wordDB.thumbsUpNumber = word.thumbsUpNumber
        wordDB.word = word.word
        wordDB.writtenOn = word.writtenOn
        wordDB.isSynced = false

        launchWordAdd(wordDB)
    }

    private fun launchWordAdd(word: WordDB) = launch(coroutinesContextProvider.io) {
        urbanLocalUseCase.addWord(word)

        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val wordBody = WordBody(
                word.definition!!,
                word.thumbsUpNumber!!,
                word.authorName!!,
                word.word!!,
                word.defId!!,
                word.writtenOn!!,
                word.example!!,
                word.thumbsDownNumber!!,
                sharedDataSource.userId.toInt(),
                word.isFavorite!!
        )


        val data = Data.Builder()
                .putString("wordBody", Gson().toJson(wordBody))
                .putString("userId", sharedDataSource.userId)
                .build()

        val worker = OneTimeWorkRequest.Builder(AddFavoriteWordJob::class.java)
                .setInputData(data)
                .addTag(Const.WORK_FAVORITE)
                .setConstraints(constraints)
                .build()
        workManager.enqueue(worker)

    }


    fun isFavorite(word: Word) {
        launch(coroutinesContextProvider.io) {
            val result = urbanLocalUseCase.isExist(word.defId)
            withContext(coroutinesContextProvider.io) {
                _isFavorite.postValue(result.first)
                _isSynced.postValue(result.second)
            }
        }
    }

    fun getAllWords() {
        launchGetAllWords()
    }

    private fun launchGetAllWords() = launch(coroutinesContextProvider.io) {
        val result = urbanLocalUseCase.getAllWords()
        withContext(coroutinesContextProvider.io) { onResultWords(result) }
    }

    private fun onResultWords(result: List<WordDB>) {
        val listWords = ArrayList<Word>()
        result.forEach {
            val word = Word(
                    it.definition!!,
                    "",
                    it.thumbsUpNumber!!,
                    it.defId!!,
                    it.authorName!!,
                    it.word!!,
                    it.writtenOn!!,
                    ArrayList(),
                    it.example!!,
                    it.thumbsDownNumber!!
            )
            listWords.add(word)
        }
        emitFavWords(listWords)
    }

    private fun launchDefinition(word: String) = launch(coroutinesContextProvider.io) {
        val result = urbanUseCase(word)
        withContext(coroutinesContextProvider.io) { onResult(result) }
    }


    private fun onResult(result: Result<BaseResponse>) {
        progress.postValue(false)
        when (result) {
            is Result.Success -> {
                emitUiModel(result.data)
            }
            is Result.Error -> {
                errorBase.postValue(result.exception as ErrorKit?)
            }
        }
    }

    private fun emitUiModel(data: BaseResponse) =
            launch(coroutinesContextProvider.main) {
                _uiModel.value = data.wordList
            }

    private fun emitFavWords(listWords: List<Word>) =
            launch(coroutinesContextProvider.main) {
                _allFavoriteWords.value = listWords
            }

}

