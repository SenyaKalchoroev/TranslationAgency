package com.kwork.translationagency.domain.repositories

import kotlinx.coroutines.flow.Flow

interface SettingsPreferencesRepository {
    fun isLoggedIn(): Flow<Boolean>
    suspend fun saveLoginStatus(loggedIn: Boolean)
}
