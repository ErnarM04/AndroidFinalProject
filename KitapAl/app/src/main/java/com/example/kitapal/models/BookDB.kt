package com.example.kitapal.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookDB(
    @PrimaryKey
    val id: String,
    val title: String,
    val cover: String,
    val authors: List<String>,
    val price: Double,
    val categories: List<String>
)
