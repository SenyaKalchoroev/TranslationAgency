package com.kwork.translationagency.domain.usecase

import com.kwork.translationagency.domain.repositories.SettingsPreferencesRepository
import javax.inject.Inject

class SaveLoginStatusUseCase @Inject constructor(
    private val prefsRepo: SettingsPreferencesRepository
) {
    suspend operator fun invoke(loggedIn: Boolean) {
        prefsRepo.saveLoginStatus(loggedIn)
    }
}
