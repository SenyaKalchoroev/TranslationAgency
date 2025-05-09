package com.kwork.translationagency.di

import com.kwork.translationagency.domain.usecase.SignInUseCase
import com.kwork.translationagency.domain.repositories.AuthRepository
import com.kwork.translationagency.domain.repositories.ClientsRepository
import com.kwork.translationagency.domain.repositories.OrdersRepository
import com.kwork.translationagency.domain.repositories.SettingsPreferencesRepository
import com.kwork.translationagency.domain.usecase.AddOrderUseCase
import com.kwork.translationagency.domain.usecase.GetClientUseCase
import com.kwork.translationagency.domain.usecase.GetClientsUseCase
import com.kwork.translationagency.domain.usecase.GetLoginStatusUseCase
import com.kwork.translationagency.domain.usecase.GetOrdersUseCase
import com.kwork.translationagency.domain.usecase.SaveLoginStatusUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideSignInUseCase(
        authRepository: AuthRepository
    ): SignInUseCase =
        SignInUseCase(authRepository)

    @Provides
    @Singleton
    fun provideClientsUseCase(
        clientRepository: ClientsRepository
    ): GetClientsUseCase =
        GetClientsUseCase(clientRepository)
    @Provides
    @Singleton
    fun provideClientUseCase(
        clientRepository: ClientsRepository
    ): GetClientUseCase =
        GetClientUseCase(clientRepository)

    @Provides
    @Singleton
    fun provideGetLoginStatusUseCase(
        repo: SettingsPreferencesRepository
    ): GetLoginStatusUseCase =
        GetLoginStatusUseCase(repo)

    @Provides
    @Singleton
    fun provideGetOrdersUseCase(
        repo: OrdersRepository
    ): GetOrdersUseCase =
        GetOrdersUseCase(repo)

    @Provides
    @Singleton
    fun provideSaveLoginStatusUseCase(
        repo: SettingsPreferencesRepository
    ): SaveLoginStatusUseCase =
        SaveLoginStatusUseCase(repo)

    @Provides
    @Singleton
    fun provideAddOrderUseCase(
        repo: OrdersRepository
    ): AddOrderUseCase =
        AddOrderUseCase(repo)


}
