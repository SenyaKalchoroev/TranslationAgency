package com.kwork.translationagency.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.kwork.translationagency.core.base.BaseRepository
import com.kwork.translationagency.core.common.Either
import com.kwork.translationagency.data.remote.model.OrderCreateDto
import com.kwork.translationagency.data.remote.model.OrderDto
import com.kwork.translationagency.domain.model.OrderModel
import com.kwork.translationagency.domain.repositories.OrdersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
  private val firestore: FirebaseFirestore
) : BaseRepository(), OrdersRepository {

  override fun getOrders(): Flow<Either<String, List<OrderModel>>> =
    makeNetworkRequest {
      firestore.collection("orders")
        .get()
        .await()
        .toObjects(OrderDto::class.java)
        .map { it.toDomain() }
    }

  override fun addOrder(dto: OrderCreateDto): Flow<Either<String, Unit>> =
    makeNetworkRequest {
      val maxId = firestore.collection("orders")
        .orderBy("id", Query.Direction.DESCENDING)
        .limit(1)
        .get().await()
        .documents.firstOrNull()
        ?.getString("id")?.toIntOrNull() ?: 0

      val map = hashMapOf(
        "id"          to (maxId + 1).toString(),
        "username"    to dto.username,
        "description" to dto.description,
        "price"       to dto.price,
        "dateFrom"    to dto.dateFrom,
        "dateTo"      to dto.dateTo,
        "langFrom"    to dto.langFrom,
        "langTo"      to dto.langTo,
        "status"      to dto.status,
        "orderStatus" to dto.orderStatus
      )
      firestore.collection("orders").add(map).await()
      Unit
    }
  override fun getOrdersByDate(date: String) = makeNetworkRequest {
    firestore.collection("orders")
      .whereEqualTo("dateFrom", date)
      .get().await()
      .toObjects(OrderDto::class.java)
      .map { it.toDomain() }
  }

}
