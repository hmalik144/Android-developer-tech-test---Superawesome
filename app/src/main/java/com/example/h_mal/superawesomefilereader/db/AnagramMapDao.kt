package com.example.h_mal.superawesomefilereader.db

import android.provider.SyncStateContract.Helpers.update
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.h_mal.superawesomefilereader.db.entities.AnagramMap
import com.example.h_mal.superawesomefilereader.db.entities.AnagramPairs
import java.lang.StringBuilder


@Dao
interface AnagramMapDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAnagramPair(anagramMap : AnagramMap):Long

    @Query("UPDATE AnagramMap SET listOfAnagrams = (listOfAnagrams || ',' || (:s2)) WHERE word = (:s1)")
    suspend fun update(s1: String, s2 : String)

    @Query("SELECT listOfAnagrams FROM AnagramMap")
    fun getAnagramPairList() : LiveData<List<String>>

    @Query("DELETE FROM AnagramMap")
    suspend fun deleteAnagramPairs()

    @Query("SELECT * FROM AnagramMap WHERE word is (:firstWord) LIMIT 1")
    suspend fun getItem(firstWord : String): AnagramMap?

    @Transaction
    suspend fun upsert(anagramPairs : AnagramPairs) {
        val item = saveAnagramPair(AnagramMap(anagramPairs.alphabeticOrderedWord, anagramPairs.word))

        if (item.toInt() == -1){
            update(anagramPairs.alphabeticOrderedWord,anagramPairs.word)
        }
    }

}