package com.kwork.translationagency.data.remote.mapper

interface DataMapper<T> {
    fun toDomain():T
}