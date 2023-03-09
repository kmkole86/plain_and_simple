package com.kmkole86.remote.data_source

import com.kmkole86.remote.BuildConfig
import com.kmkole86.remote.model.PageResponse
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

const val PAGE_PARAMETER_KEY = "page"

interface MovieRemoteDataSource {
    suspend fun fetchMovies(pageOrdinal: Int): PageResponse
}

class MovieRemoteDataSourceImpl @Inject constructor(private val client: HttpClient) :
    MovieRemoteDataSource {

    override suspend fun fetchMovies(pageOrdinal: Int): PageResponse {
        return client.get(BuildConfig.API_URL + "top_rated") {
            url {
                parameters.append(PAGE_PARAMETER_KEY, pageOrdinal.toString())
            }
        }
    }
}