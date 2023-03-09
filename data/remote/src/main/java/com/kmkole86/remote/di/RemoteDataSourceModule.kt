package com.kmkole86.remote.di

import com.kmkole86.remote.data_source.MovieRemoteDataSource
import com.kmkole86.remote.data_source.MovieRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RemoteDataSourceModule {

    @Binds
    @Reusable
    fun provideMovieRemoteDataSource(dataSource: MovieRemoteDataSourceImpl): MovieRemoteDataSource
}