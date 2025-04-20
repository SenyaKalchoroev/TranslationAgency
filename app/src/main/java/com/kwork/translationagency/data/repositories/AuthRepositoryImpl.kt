package com.kwork.translationagency.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.kwork.translationagency.core.base.BaseRepository
import com.kwork.translationagency.core.common.Either
import com.kwork.translationagency.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : BaseRepository(), AuthRepository {

    override fun signIn(loginOrEmail: String, password: String): Flow<Either<String, FirebaseUser>> =
        makeNetworkRequest(request = { performSignIn(loginOrEmail, password) })

    private suspend fun performSignIn(loginOrEmail: String, password: String): FirebaseUser =
        suspendCancellableCoroutine { cont ->
            fun doSignIn(email: String) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            auth.currentUser?.let { cont.resume(it) }
                                ?: cont.resumeWithException(NullPointerException("User is null"))
                        } else {
                            cont.resumeWithException(task.exception ?: Exception("Authentication failed"))
                        }
                    }
            }

            if (loginOrEmail.contains("@")) {
                doSignIn(loginOrEmail)
            } else {
                firestore.collection("users")
                    .whereEqualTo("username", loginOrEmail)
                    .get()
                    .addOnSuccessListener { snapshot ->
                        if (snapshot.isEmpty) {
                            cont.resumeWithException(Exception("Пользователь не найден"))
                        } else {
                            val email = snapshot.documents.first().getString("email")
                            if (email.isNullOrBlank()) {
                                cont.resumeWithException(Exception("Email не указан в профиле"))
                            } else {
                                doSignIn(email)
                            }
                        }
                    }
                    .addOnFailureListener { e ->
                        cont.resumeWithException(e)
                    }
            }
        }
}