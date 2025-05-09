package com.kwork.translationagency.data.remote.model

import com.kwork.translationagency.domain.model.ClientModel

data class ClientDto(
    val name: String       = "",
    val nickname: String   = "",
    val phone: String      = "",
    val avatarUrl: String  = ""
) {
    fun toDomain(documentId: String) = ClientModel(
        id        = documentId,
        name      = name,
        nickname  = nickname,
        phone     = phone,
        avatarUrl = avatarUrl
    )
}


