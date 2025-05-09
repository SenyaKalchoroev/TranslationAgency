package com.kwork.translationagency.presentation.viewmodel.calendar

import com.kwork.translationagency.core.base.BaseViewModel
import com.kwork.translationagency.domain.usecase.GetOrdersByDateUseCase
import com.kwork.translationagency.presentation.model.clients.OrderUi
import com.kwork.translationagency.presentation.model.clients.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getOrdersByDate: GetOrdersByDateUseCase
) : BaseViewModel() {

    private val _orders = mutableStateFlow<List<OrderUi>>()
    val orders = _orders.asStateFlow()

    fun load(date: String) {
        getOrdersByDate(date).gatherRequest(_orders) { list ->
            list.map { it.toUI() }
        }
    }
}
