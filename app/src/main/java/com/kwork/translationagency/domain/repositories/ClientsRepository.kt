package com.kwork.translationagency.domain.repositories

import com.kwork.translationagency.core.common.Either
import com.kwork.translationagency.domain.model.ClientModel
import kotlinx.coroutines.flow.Flow

interface ClientsRepository {
  fun getClients(): Flow<Either<String, List<ClientModel>>>
  fun getClientById(id: String): Flow<Either<String, ClientModel>>
}
