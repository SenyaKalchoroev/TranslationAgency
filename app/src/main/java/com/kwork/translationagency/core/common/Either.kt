package com.kwork.translationagency.core.common

sealed class Either<out A, out B> {
    class Left<out A>(val value: A) : Either<A, Nothing>()
    class Right<out B>(val value: B) : Either<Nothing, B>()
}

fun <L, R, T> Either<L, R>.fold(
    onLeft: (L) -> T,
    onRight: (R) -> T
): T = when (this) {
    is Either.Left -> onLeft(value)
    is Either.Right -> onRight(value)
}