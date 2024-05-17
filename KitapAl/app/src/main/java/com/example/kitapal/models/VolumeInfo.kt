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
    val previewLink: String,
    val infoLink: String
)

data class imageLinks(
    val thumbnail: String,
    val smallThumbnail: String
)

data class SaleInfo(
    val listPrice: ListPrice,
)

data class ListPrice(
    val amount: Double,
    val currencyCode: String
)