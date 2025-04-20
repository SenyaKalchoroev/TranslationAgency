package com.kwork.translationagency.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kwork.translationagency.core.common.Either
import com.kwork.translationagency.core.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected fun <T> mutableStateFlow(): MutableStateFlow<UiState<T>> =
        MutableStateFlow(UiState.Loading())

    protected fun <T, S> gatherRequest(
        flow: Flow<Either<String, T>>,
        state: MutableStateFlow<UiState<S>>,
        mappedData: (data: T) -> S,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = UiState.Loading()
            flow.collect { either ->
                when (either) {
                    is Either.Left -> {
                        state.value = UiState.Error(either.value)
                    }

                    is Either.Right -> {
                        state.value = UiState.Success(mappedData(either.value))
                    }
                }
            }
        }
    }

}