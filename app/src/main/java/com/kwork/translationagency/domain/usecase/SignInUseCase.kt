package com.kwork.translationagency.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.kwork.translationagency.core.common.Either
import com.kwork.translationagency.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(loginOrEmail: String, password: String): Flow<Either<String, FirebaseUser>> =
        authRepository.signIn(loginOrEmail, password)
}