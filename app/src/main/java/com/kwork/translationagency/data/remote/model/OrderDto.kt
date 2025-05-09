package com.kwork.translationagency.data.remote.model

import com.kwork.translationagency.data.remote.mapper.DataMapper
import com.kwork.translationagency.domain.model.OrderModel

data class OrderDto(
    var id          : String = "",
    var username    : String = "",
    var description : String = "",
    var price       : String = "",
    var dateFrom    : String = "",
    var dateTo      : String = "",
    var status      : Boolean = true,
    var orderStatus : String = ""
) : DataMapper<OrderModel> {
    override fun toDomain() = OrderModel(
        id          = id.toIntOrNull() ?: 0,
        userName    = username,
        description = description,
        price       = price,
        dateFrom    = dateFrom,
        dateTo      = dateTo,
        isNew       = status,
        orderStatus = orderStatus
    )
}


