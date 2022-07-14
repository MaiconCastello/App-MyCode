package com.generation.mycode.di

import com.generation.mycode.api.PublicacoesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun returnRepository(): PublicacoesRepository{
        return PublicacoesRepository()
    }
}