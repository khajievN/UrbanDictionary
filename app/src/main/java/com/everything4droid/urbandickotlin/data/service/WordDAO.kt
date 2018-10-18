package com.everything4droid.urbandickotlin.data.service

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.everything4droid.urbandickotlin.data.entity.WordDB
import androidx.room.RoomMasterTable.TABLE_NAME


/**
 * Created by Khajiev Nizomjon on 10/10/2018.
 */
@Dao
interface WordDAO {

    @Insert(
            onConflict = OnConflictStrategy.REPLACE
    )
    fun saveWord(wordDB: WordDB)

    @Update(onConflict = REPLACE)
    fun updateWord(wordDB: WordDB): Int

    @Query("UPDATE word SET isFavorite=:isFavorite WHERE defId=:defId")
    fun updateWordSpecific(isFavorite: Boolean, defId: Int)

    @Query("UPDATE word SET isSynced=:isSynced WHERE defId=:defId")
    fun updateSyncedStatus(isSynced: Boolean, defId: Int)

    @Query("SELECT * FROM word")
    fun getWordList(): List<WordDB>

    @Query("SELECT * FROM word WHERE word.defId==:defId")
    fun getWordByDefId(defId: Int): WordDB?

    @Query("DELETE FROM word WHERE word.defId==:id")
    fun deleteById(id: Long): Int

    @Delete
    fun delete(wordDB: WordDB)

}