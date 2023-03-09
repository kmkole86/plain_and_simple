package com.kmkole86.domain.repository

import com.kmkole86.domain.entity.Page
import com.kmkole86.domain.result.Result

interface MovieRepository {

    suspend fun getMovies(pageOrdinal: Int): Result<Page>
}