package com.kwork.translationagency.di

import com.kwork.translationagency.domain.usecase.SignInUseCase
import com.kwork.translationagency.domain.repositories.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides @Singleton
    fun provideSignInUseCase(
        authRepository: AuthRepository
    ): SignInUseCase =
        SignInUseCase(authRepository)
}
