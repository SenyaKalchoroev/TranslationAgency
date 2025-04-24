package com.kwork.translationagency.core.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kwork.translationagency.core.common.Either
import com.kwork.translationagency.core.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    protected fun <T> mutableStateFlow() = MutableStateFlow<UiState<T>>(UiState.Idle())
    protected fun <T, S> Flow<Either<String, T>>.gatherRequest(
        state: MutableStateFlow<UiState<S>>,
        mappedData: (data: T) -> S
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = UiState.Loading()
            this@gatherRequest.collect {
                when (it) {
                    is Either.Left -> {
                        Log.e("BaseViewModel", "Error: ${it.value}")
                        state.value = UiState.Error(it.value)
                    }
                    is Either.Right -> {
                        Log.e("BaseViewModel", "Data received: ${it.value}")
                        state.value = UiState.Success(mappedData(it.value))
                    }
                }
            }
        }
    }
}