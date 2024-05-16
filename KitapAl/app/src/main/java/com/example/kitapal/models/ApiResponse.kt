package com.example.kitapal.models

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val kind: String,
    val totalItems: Int,
    @SerializedName("items") val books: List<Book>
)
