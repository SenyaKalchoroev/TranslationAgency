package com.kwork.translationagency.domain.model

data class OrderModel(
    val id: Int,
    val userName: String,
    val description: String,
    val price: String,
    val dateFrom: String,
    val dateTo: String
)