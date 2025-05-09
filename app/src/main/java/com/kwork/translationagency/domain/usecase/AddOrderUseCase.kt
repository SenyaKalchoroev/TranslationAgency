package com.kwork.translationagency.domain.usecase

import com.kwork.translationagency.data.remote.model.OrderCreateDto
import com.kwork.translationagency.domain.repositories.OrdersRepository
import javax.inject.Inject

class AddOrderUseCase @Inject constructor(
    private val repo: OrdersRepository
) {
    operator fun invoke(dto: OrderCreateDto) = repo.addOrder(dto)
}
