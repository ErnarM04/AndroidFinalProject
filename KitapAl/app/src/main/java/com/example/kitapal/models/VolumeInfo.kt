package com.example.kitapal.models

data class VolumeInfo(
    val title: String,
    val authors: List<String>,
    val publisher: String,
    val publishedDate: String,
    val description: String,
    val imageLinks: imageLinks,
    val pageCount: Int,
    val categories: List<String>,
    val language: String,
    val previewLink: String,
    val infoLink: String
)

data class imageLinks(
    val thumbnail: String,
    val smallThumbnail: String
)