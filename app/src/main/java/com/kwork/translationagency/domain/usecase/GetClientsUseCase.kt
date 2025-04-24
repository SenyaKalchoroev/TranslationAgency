package com.kwork.translationagency.domain.usecase

import com.kwork.translationagency.core.common.Either
import com.kwork.translationagency.domain.model.ClientModel
import com.kwork.translationagency.domain.repositories.ClientsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClientsUseCase @Inject constructor(
    private val repo: ClientsRepository
) {
    operator fun invoke(): Flow<Either<String, List<ClientModel>>> =
        repo.getClients()
}



