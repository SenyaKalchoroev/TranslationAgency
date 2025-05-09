package com.kwork.translationagency.presentation.model.clients

import com.kwork.translationagency.domain.model.ClientModel

data class ClientsUi (
    val id: String,
    val name: String,
    val nickname: String,
    val phone: String,
    val avatarUrl: String,
    val isNew: Boolean = false,
    val isActive: Boolean = true,
    val hasOrder: Boolean = false,
    val isChecked: Boolean = false
)
fun ClientModel.toUI() = ClientsUi(
    id = id,
    name = name,
    nickname = nickname,
    phone = phone,
    avatarUrl = avatarUrl
)