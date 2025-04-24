package com.kwork.translationagency.data.local

import android.content.SharedPreferences
import com.kwork.translationagency.core.common.Constants.KEY_IS_LOGGED_IN
import com.kwork.translationagency.domain.repositories.SettingsPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import androidx.core.content.edit


class SettingsPreferencesImpl @Inject constructor(
    private val prefs: SharedPreferences
) : SettingsPreferencesRepository {

    override fun isLoggedIn(): Flow<Boolean> =
        flow {
            emit(prefs.getBoolean(KEY_IS_LOGGED_IN, false))
        }

    override suspend fun saveLoginStatus(loggedIn: Boolean) {
        prefs.edit{
                putBoolean(KEY_IS_LOGGED_IN, loggedIn)
        }
    }
}
