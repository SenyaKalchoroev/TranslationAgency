package com.kwork.translationagency.domain.usecase

import com.kwork.translationagency.domain.repositories.OrdersRepository
import javax.inject.Inject

class GetOrdersByDateUseCase @Inject constructor(
    private val repo: OrdersRepository
) {
    operator fun invoke(date: String) = repo.getOrdersByDate(date)
}
