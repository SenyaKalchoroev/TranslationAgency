package com.kwork.translationagency.presentation.viewmodel.profile

import com.kwork.translationagency.core.base.BaseViewModel
import com.kwork.translationagency.domain.usecase.GetClientUseCase
import com.kwork.translationagency.presentation.model.clients.ClientsUi
import com.kwork.translationagency.presentation.model.clients.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
  private val getClientUseCase: GetClientUseCase
) : BaseViewModel() {

  private val _clientState = mutableStateFlow<ClientsUi>()
  val clientState = _clientState.asStateFlow()

  fun loadClient(clientId: String) {
    getClientUseCase(clientId)
      .gatherRequest(_clientState) { it.toUI() }
  }
}

