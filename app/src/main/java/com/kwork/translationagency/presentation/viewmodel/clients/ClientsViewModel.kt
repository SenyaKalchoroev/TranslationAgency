package com.kwork.translationagency.presentation.viewmodel.clients

import com.kwork.translationagency.core.base.BaseViewModel
import com.kwork.translationagency.domain.usecase.GetClientsUseCase
import com.kwork.translationagency.presentation.model.clients.ClientsUi
import com.kwork.translationagency.presentation.model.clients.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ClientsViewModel @Inject constructor(
    private val getClients: GetClientsUseCase
) : BaseViewModel() {

    private val _clientsState = mutableStateFlow<List<ClientsUi>>()   // UiState<List<…>>
    val clientsState = _clientsState.asStateFlow()

    fun loadClients() {
        getClients().gatherRequest(_clientsState) { list ->
            list.map { it.toUI() }
        }
    }
}

