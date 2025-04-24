package com.kwork.translationagency.presentation.viewmodel.orders

import com.kwork.translationagency.core.base.BaseViewModel
import com.kwork.translationagency.data.remote.model.OrderCreateDto
import com.kwork.translationagency.domain.usecase.AddOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NewOrderViewModel @Inject constructor(
    private val addOrder: AddOrderUseCase
) : BaseViewModel() {

    private val _state = mutableStateFlow<Unit>()
    val state = _state.asStateFlow()

    fun save(dto: OrderCreateDto) {
        addOrder(dto).gatherRequest(_state) { Unit }
    }
}
