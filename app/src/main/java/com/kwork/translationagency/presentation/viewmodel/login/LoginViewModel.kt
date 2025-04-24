package com.kwork.translationagency.presentation.viewmodel.login

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.kwork.translationagency.core.base.BaseViewModel
import com.kwork.translationagency.core.common.Either
import com.kwork.translationagency.core.common.UiState
import com.kwork.translationagency.domain.usecase.GetLoginStatusUseCase
import com.kwork.translationagency.domain.usecase.SaveLoginStatusUseCase
import com.kwork.translationagency.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val saveLoginStatus: SaveLoginStatusUseCase,
    private val getLoginStatus: GetLoginStatusUseCase
) : BaseViewModel() {

    private val _loginState = MutableStateFlow<UiState<FirebaseUser>>(UiState.Idle())
    val loginState: StateFlow<UiState<FirebaseUser>> = _loginState.asStateFlow()

    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn.asStateFlow()

    init {
        viewModelScope.launch {
            getLoginStatus()
                .collect { flag ->
                    _isLoggedIn.value = flag
                }
        }
    }

    fun signIn(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = UiState.Error("Логин и пароль не должны быть пустыми")
            return
        }

        _loginState.value = UiState.Loading()

        viewModelScope.launch {
            signInUseCase(email, password)
                .collect { either ->
                    when (either) {
                        is Either.Right -> {
                            saveLoginStatus(true)
                            _loginState.value = UiState.Success(either.value)
                        }
                        is Either.Left -> {
                            _loginState.value = UiState.Error(either.value)
                        }
                    }
                }
        }
    }
}
