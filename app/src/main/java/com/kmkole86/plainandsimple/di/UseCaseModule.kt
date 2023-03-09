package com.kmkole86.plainandsimple.di

import com.kmkole86.domain.repository.MovieRepository
import com.kmkole86.domain.use_case.GetMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@InstallIn(SingletonComponent::class)
@Module
object UseCasesModule {

    @Provides
    @Reusable
    fun provideGetMoviesUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        movieRepository: MovieRepository
    ): GetMoviesUseCase = GetMoviesUseCase(dispatcher, movieRepository)
}