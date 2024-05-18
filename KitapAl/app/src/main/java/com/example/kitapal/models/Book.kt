package com.example.kitapal.models

data class Book(
    open val id: String,
    open val volumeInfo: VolumeInfo,
    open val saleInfo: SaleInfo
)
