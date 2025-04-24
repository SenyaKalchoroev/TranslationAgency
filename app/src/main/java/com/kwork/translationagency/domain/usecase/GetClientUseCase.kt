package com.kwork.translationagency.domain.usecase

import com.kwork.translationagency.domain.repositories.ClientsRepository
import javax.inject.Inject

class GetClientUseCase @Inject constructor(
  private val repo: ClientsRepository
) {
  operator fun invoke(clientId: String) = repo.getClientById(clientId)
}