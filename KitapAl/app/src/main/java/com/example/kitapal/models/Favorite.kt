package com.example.kitapal.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey
    val userID: Int,
    val bookIDs: String
)

