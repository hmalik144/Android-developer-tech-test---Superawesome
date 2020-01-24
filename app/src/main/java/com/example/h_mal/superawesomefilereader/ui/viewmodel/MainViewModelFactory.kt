package com.example.h_mal.superawesomefilereader.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.h_mal.superawesomefilereader.repository.Repo

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val repository: Repo
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            repository
        ) as T
    }
}