package com.example.h_mal.superawesomefilereader.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnagramMap(
    @PrimaryKey(autoGenerate = false)
    val word: String,
    val listOfAnagrams: String
)