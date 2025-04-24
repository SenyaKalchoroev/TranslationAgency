package com.kwork.translationagency.di

import com.kwork.translationagency.data.local.SettingsPreferencesImpl
import com.kwork.translationagency.data.repositories.AuthRepositoryImpl
import com.kwork.translationagency.data.repositories.ClientsRepositoryImpl
import com.kwork.translationagency.data.repositories.OrdersRepositoryImpl
import com.kwork.translationagency.domain.repositories.AuthRepository
import com.kwork.translationagency.domain.repositories.ClientsRepository
import com.kwork.translationagency.domain.repositories.OrdersRepository
import com.kwork.translationagency.domain.repositories.SettingsPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository
    @Binds
    abstract fun bindClientsRepository(
        impl: ClientsRepositoryImpl
    ): ClientsRepository
    @Binds
    abstract fun bindSettingsPrefs(
        impl: SettingsPreferencesImpl
    ): SettingsPreferencesRepository
    @Binds
    abstract fun bindOrdersRepository(
        impl: OrdersRepositoryImpl
    ): OrdersRepository

}
