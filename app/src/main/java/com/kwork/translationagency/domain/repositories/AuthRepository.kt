package com.kwork.translationagency.domain.repositories

import com.google.firebase.auth.FirebaseUser
import com.kwork.translationagency.core.common.Either
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signIn(loginOrEmail: String, password: String): Flow<Either<String, FirebaseUser>>
}