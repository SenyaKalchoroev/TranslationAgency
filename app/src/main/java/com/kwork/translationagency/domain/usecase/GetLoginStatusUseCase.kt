package com.kwork.translationagency.domain.usecase

import com.kwork.translationagency.domain.repositories.SettingsPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoginStatusUseCase @Inject constructor(
    private val prefsRepo: SettingsPreferencesRepository
) {
    operator fun invoke(): Flow<Boolean> =
        prefsRepo.isLoggedIn()
}
