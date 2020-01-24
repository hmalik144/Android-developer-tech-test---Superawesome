package com.example.h_mal.superawesomefilereader.ui

interface ExecutionListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}