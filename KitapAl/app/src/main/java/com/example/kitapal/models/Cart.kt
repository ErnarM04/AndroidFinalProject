package com.example.kitapal.models

import androidx.room.Entity

@Entity
data class Cart(
    val userID: Int,
    val bookIDs: List<String>
)
