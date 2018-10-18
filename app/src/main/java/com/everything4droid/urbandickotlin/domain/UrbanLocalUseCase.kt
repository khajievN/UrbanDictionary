package com.everything4droid.urbandickotlin.domain

import com.everything4droid.urbandickotlin.data.entity.WordDB
import com.everything4droid.urbandickotlin.data.repository.UrbanLocalRepository

/**
 * Created by Khajiev Nizomjon on 10/10/2018.
 */
class UrbanLocalUseCase(private val urbanLocalRepository: UrbanLocalRepository) {

    suspend fun getAllWords(): List<WordDB> {
        return urbanLocalRepository.getAllWordEntries()
    }


    suspend fun isExist(defId: Int): Pair<Boolean?, Boolean?> {
        val result = urbanLocalRepository.getWordById(defId)
        return if (result != null) {
            return Pair(result.isFavorite, result.isSynced)
        } else {
            Pair(false, false)
        }
    }


    suspend fun addWord(wordDB: WordDB) {
        val tempWordDB = urbanLocalRepository.getWordById(wordDB.defId!!)
        if (tempWordDB != null) {
            urbanLocalRepository.updateWord(!tempWordDB.isFavorite!!, wordDB.defId!!)
        } else {
            wordDB.isFavorite = true
            urbanLocalRepository.insertWord(wordDB)
        }
    }

    suspend fun updateSyncStatus(isSynced: Boolean, defId: Int) {
        val temWordDB = urbanLocalRepository.getWordById(defId)
        if (temWordDB != null) {
            urbanLocalRepository.updateSyncStatus(isSynced, defId)
        }
    }

}