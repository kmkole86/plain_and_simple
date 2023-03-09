package com.kmkole86.data.di

import com.kmkole86.data.repository.MovieRepositoryImpl
import com.kmkole86.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Reusable
    abstract fun provideMovieRepository(repository: MovieRepositoryImpl): MovieRepository
}