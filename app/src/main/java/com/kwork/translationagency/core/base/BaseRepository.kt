package com.kwork.translationagency.core.base

import com.kwork.translationagency.core.common.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseRepository {

    fun <T> makeNetworkRequest(
        gatherIsSucceed: ((T) -> Unit)? = null,
        request: suspend () -> T,
    ) = flow<Either<String, T>> {
        request().also {
            gatherIsSucceed?.invoke(it)
            emit(Either.Right(value = it))
        }
    }.flowOn(Dispatchers.IO).catch { e ->
        emit(Either.Left(value = e.localizedMessage ?: "Error Occurred!"))
    }

}