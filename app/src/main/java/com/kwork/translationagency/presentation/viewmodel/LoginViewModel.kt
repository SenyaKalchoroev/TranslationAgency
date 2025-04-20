package com.kwork.translationagency.presentation.viewmodel

import com.google.firebase.auth.FirebaseUser
import com.kwork.translationagency.core.base.BaseViewModel
import com.kwork.translationagency.core.common.UiState
import com.kwork.translationagency.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : BaseViewModel() {

    private val _loginState = mutableStateFlow<FirebaseUser>()
    val loginState = _loginState

    fun signIn(loginOrEmail: String, password: String) {
        if (loginOrEmail.isBlank() || password.isBlank()) {
            _loginState.value = UiState.Error("Логин и пароль не должны быть пустыми")
            return
        }
        gatherRequest(
            flow       = signInUseCase(loginOrEmail, password),
            state      = _loginState,
            mappedData = { it }
        )
    }
}