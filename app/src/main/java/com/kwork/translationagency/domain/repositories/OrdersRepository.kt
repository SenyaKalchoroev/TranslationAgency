package com.kwork.translationagency.domain.repositories

import com.kwork.translationagency.core.common.Either
import com.kwork.translationagency.data.remote.model.OrderCreateDto
import com.kwork.translationagency.domain.model.OrderModel
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {
  fun getOrders(): Flow<Either<String, List<OrderModel>>>
  fun addOrder(dto: OrderCreateDto): Flow<Either<String, Unit>>
  fun getOrdersByDate(date: String): Flow<Either<String, List<OrderModel>>>
}
