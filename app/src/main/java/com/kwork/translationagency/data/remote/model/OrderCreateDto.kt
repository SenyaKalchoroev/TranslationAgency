package com.kwork.translationagency.data.remote.model

data class OrderCreateDto(
    val clientId: String,
    val username    : String,
    val description : String,
    val price       : String,
    val dateFrom    : String,
    val dateTo      : String,
    val langFrom    : String,
    val langTo      : String,
    val status      : Boolean,
    val orderStatus : String
)
