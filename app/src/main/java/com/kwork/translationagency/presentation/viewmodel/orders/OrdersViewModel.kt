package com.kwork.translationagency.presentation.viewmodel.orders

import com.kwork.translationagency.core.base.BaseViewModel
import com.kwork.translationagency.domain.usecase.GetOrdersUseCase
import com.kwork.translationagency.presentation.model.clients.OrderUi
import com.kwork.translationagency.presentation.model.clients.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
  private val getOrdersUseCase: GetOrdersUseCase
) : BaseViewModel() {

  private val _ordersState = mutableStateFlow<List<OrderUi>>()
  val ordersState = _ordersState.asStateFlow()

  fun loadOrders() {
    getOrdersUseCase()
      .gatherRequest(_ordersState) { domainList ->
        domainList.map { it.toUI() }
      }
  }
}
