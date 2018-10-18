package com.everything4droid.urbandickotlin.data.repository

import com.everything4droid.urbandickotlin.data.entity.WordDB
import com.everything4droid.urbandickotlin.data.service.WordDAO
import kotlinx.coroutines.experimental.*

/**
 * Created by Khajiev Nizomjon on 10/10/2018.
 */
class UrbanLocalRepository(private val wordDAO: WordDAO) {


    suspend fun getAllWordEntries(): List<WordDB> {
        return withContext(DefaultDispatcher) {
            wordDAO.getWordList()
        }
    }

    suspend fun getWordById(defId: Int): WordDB? {
        return withContext(DefaultDispatcher) {
            wordDAO.getWordByDefId(defId)
        }
    }

    fun insertWord(wordDB: WordDB): Job {
        return launch {
            wordDAO.saveWord(wordDB)
        }
    }

    fun updateWord(isFavorite: Boolean, defId: Int): Job {
        return launch {
            wordDAO.updateWordSpecific(isFavorite, defId)
        }
    }

    fun updateSyncStatus(isSynced: Boolean, defId: Int): Job {
        return launch {
            wordDAO.updateSyncedStatus(isSynced, defId)
        }
    }

    fun deleteWord(wordId: Int): Job {
        return launch {
            wordDAO.deleteById(wordId.toLong())
        }
    }
}