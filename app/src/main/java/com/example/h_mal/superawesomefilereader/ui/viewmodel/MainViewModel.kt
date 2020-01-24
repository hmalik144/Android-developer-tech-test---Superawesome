package com.example.h_mal.superawesomefilereader.ui.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.h_mal.superawesomefilereader.R
import com.example.h_mal.superawesomefilereader.repository.Repo
import com.example.h_mal.superawesomefilereader.ui.ExecutionListener
import kotlinx.coroutines.*


class MainViewModel(
    private val repository : Repo
) : ViewModel() {

    val item by lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            repository.getWords()
        }
    }

    var callback : ExecutionListener? = null

    suspend fun clear() = repository.clearDb()

    //viewbind to button 1 for click event
    fun onClick1(view : View){

        executeAnagramSorting(R.raw.example1)
    }

    //viewbind to button 2 for click event
    fun onClick2(view : View){
        executeAnagramSorting(R.raw.example2)
    }

    //viewbind execute repository operations
    fun executeAnagramSorting(resId : Int){
        callback?.onStarted()

        CoroutineScope(Dispatchers.Default).launch {
            repository.startOperation(resId)
            withContext(Dispatchers.Main){
                callback?.onSuccess()
            }

        }

    }

}