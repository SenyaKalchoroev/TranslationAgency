package com.kwork.translationagency.domain.usecase

import  com.kwork.translationagency.core.common.Either
import com.kwork.translationagency.domain.model.OrderModel
import com.kwork.translationagency.domain.repositories.OrdersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
  private val repo: OrdersRepository
) {
  operator fun invoke(): Flow<Either<String, List<OrderModel>>> =
    repo.getOrders()
}
