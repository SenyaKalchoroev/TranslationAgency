package com.kwork.translationagency.presentation.model.clients

import com.kwork.translationagency.domain.model.OrderModel

data class OrderUi(
    val id         : Int,
    val userName   : String,
    val description: String,
    val price      : String,
    val dateFrom   : String,
    val dateTo     : String,
    val isNew      : Boolean,
    val orderStatus: String
)

fun OrderModel.toUI() = OrderUi(
    id = id,
    userName = userName,
    description = description,
    price = price,
    dateFrom = dateFrom,
    dateTo = dateTo,
    orderStatus = orderStatus,
    isNew = isNew
)
