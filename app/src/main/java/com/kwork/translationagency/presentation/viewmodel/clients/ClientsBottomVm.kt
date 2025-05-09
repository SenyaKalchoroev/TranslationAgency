package com.kwork.translationagency.presentation.viewmodel.clients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kwork.translationagency.core.common.Either
import com.kwork.translationagency.domain.usecase.GetClientsUseCase
import com.kwork.translationagency.presentation.model.clients.ClientsUi
import com.kwork.translationagency.presentation.model.clients.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ClientsBottomVm @Inject constructor(
    getClients: GetClientsUseCase
) : ViewModel() {
    enum class Filter { ALL, NEW, ACTIVE, WITH_ORDER }
    val query    = MutableStateFlow("")
    private val filter   = MutableStateFlow(Filter.ALL)
    val selected = MutableStateFlow<MutableSet<String>>(mutableSetOf())

    private val db: StateFlow<List<ClientsUi>> = getClients()
        .map { either ->
            when (either) {
                is Either.Right -> either.value.map { it.toUI() }
                is Either.Left  -> emptyList()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    val uiClients: StateFlow<List<ClientsUi>> = combine(
        db,
        filter,
        query,
        selected
    ) { list, f, q, sel ->
        list
            .filter {
                q.isBlank()
                        || it.name.contains(q, ignoreCase = true)
                        || it.nickname.contains(q, ignoreCase = true)
            }
            .filter {
                when (f) {
                    Filter.ALL        -> true
                    Filter.NEW        -> it.isNew
                    Filter.ACTIVE     -> it.isActive
                    Filter.WITH_ORDER -> it.hasOrder
                }
            }
            .map { clientUi ->
                clientUi.copy(isChecked = clientUi.id in sel)
            }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    fun toggle(item: ClientsUi) {
        if (!selected.value.add(item.id)) {
            selected.value.remove(item.id)
        }
        selected.value = selected.value
    }

    fun setFilter(f: Filter) {
        filter.value = f
    }
}
