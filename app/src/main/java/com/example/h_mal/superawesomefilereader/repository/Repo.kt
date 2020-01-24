package com.example.h_mal.superawesomefilereader.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.h_mal.superawesomefilereader.db.AppDatabase
import com.example.h_mal.superawesomefilereader.db.entities.AnagramPairs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Repo (
    val database: AppDatabase,
    context: Context
){

    private val applicationContext = context.applicationContext

    private val words = MutableLiveData<List<String>>()

    init {
        words.observeForever {

            CoroutineScope(Dispatchers.IO).launch {
                clearDb()
                saveWords(it)
            }

        }
    }



    suspend fun startOperation(resourceId: Int) {

        return withContext(Dispatchers.IO){
            val s = textFileToStringList(resourceId)
            words.postValue(s)
        }
    }


    private suspend fun saveWords(quotes: List<String>) {
        CoroutineScope(Dispatchers.IO).launch {

            quotes.forEach {
                val anagramPair = AnagramPairs(it,it.toAlphabeticOrder())
                database.getMapDao().upsert(anagramPair)
            }

        }
    }

    //get character of string to alphabetical order
    fun String.toAlphabeticOrder() : String =  String(this.toCharArray().sortedArray())


    //Get lines of strings from text file in raw resource
    private fun textFileToStringList(resourceId: Int): List<String>  =
        applicationContext.resources.openRawResource(resourceId).reader(Charsets.US_ASCII).readLines()

    //clear database of existing values
    suspend fun clearDb() = database.getMapDao().deleteAnagramPairs()

    fun getWords(): LiveData<List<String>> = database.getMapDao().getAnagramPairList()



}