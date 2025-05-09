package com.kwork.translationagency.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.kwork.translationagency.core.base.BaseRepository
import com.kwork.translationagency.core.common.Either
import com.kwork.translationagency.data.remote.model.ClientDto
import com.kwork.translationagency.domain.model.ClientModel
import com.kwork.translationagency.domain.repositories.ClientsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ClientsRepositoryImpl @Inject constructor(
  private val firestore: FirebaseFirestore
) : BaseRepository(), ClientsRepository {

  override fun getClients(): Flow<Either<String, List<ClientModel>>> =
    makeNetworkRequest {
      firestore.collection("clients")
        .get().await()
        .documents
        .map { doc ->
          val dto = doc.toObject(ClientDto::class.java)
            ?: throw IllegalStateException("Неверные данные в clients/${doc.id}")
          dto.toDomain(doc.id)
        }
    }

  override fun getClientById(id: String): Flow<Either<String, ClientModel>> =
    makeNetworkRequest {
      val snap = firestore.collection("clients")
        .document(id)
        .get().await()
      val dto = snap.toObject(ClientDto::class.java)
        ?: throw IllegalStateException("Клиент с id=$id не найден")
      dto.toDomain(snap.id)
    }
}



