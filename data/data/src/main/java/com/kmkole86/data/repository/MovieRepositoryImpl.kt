package com.kmkole86.data.repository

import com.kmkole86.data.result.runCatchingCancelable
import com.kmkole86.domain.entity.Movie
import com.kmkole86.domain.entity.Page
import com.kmkole86.domain.repository.MovieRepository
import com.kmkole86.domain.result.Result
import com.kmkole86.local.dao.MovieDao
import com.kmkole86.local.model.MovieLocal
import com.kmkole86.local.model.PageLocal
import com.kmkole86.local.model.PopulatedPageLocal
import com.kmkole86.remote.data_source.MovieRemoteDataSource
import com.kmkole86.remote.model.MovieResponse
import com.kmkole86.remote.model.PageResponse
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * offline first repo
 */
class MovieRepositoryImpl @Inject constructor(
    private val localDataSource: MovieDao,
    private val remoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override suspend fun getMovies(pageOrdinal: Int): Result<Page> {
        val cachedPage: PopulatedPageLocal? = localDataSource.getPopulatedPage(pageOrdinal)
        if (cachedPage != null)
            return Result.Success(mapToPage(cachedPage))

        return runCatchingCancelable {
            coroutineScope {
                val result = remoteDataSource.fetchMovies(pageOrdinal = pageOrdinal)
                localDataSource.insertPage(mapToPopulatedPageLocal(result))

                localDataSource.getPopulatedPage(pageOrdinal)?.let {
                    mapToPage(it)
                }
            }
        }.fold(onSuccess = {
            if (it != null)
                Result.Success(it)
            else Result.Error()
        }, onFailure = {
            withContext(NonCancellable) {
                localDataSource.clearPageData(pageOrdinal)
            }
            Result.Error()
        })
    }
}

private fun mapToPopulatedPageLocal(value: PageResponse): PopulatedPageLocal {
    return PopulatedPageLocal(
        movies = value.movies.map { mapToMovieLocal(it, value.ordinal) },
        page = mapToPageLocal(value)
    )
}

private fun mapToPageLocal(value: PageResponse): PageLocal {
    return PageLocal(ordinal = value.ordinal, hasNext = value.ordinal < value.totalPages)
}

private fun mapToMovieLocal(value: MovieResponse, pageOrdinal: Int): MovieLocal {
    return MovieLocal(
        id = value.id,
        title = value.title,
        overview = value.overview,
        posterPath = value.posterPath,
        releaseDate = value.releaseDate,
        voteAverage = value.voteAverage,
        voteCount = value.voteCount,
        pageOrdinal = pageOrdinal
    )
}

private fun mapToPage(value: PopulatedPageLocal): Page {
    return Page(
        movies = value.movies.map { mapToMovie(it) },
        ordinal = value.page.ordinal,
        hasNext = value.page.hasNext
    )
}

private fun mapToMovie(value: MovieLocal): Movie {
    return Movie(
        id = value.id,
        title = value.title,
        overview = value.overview,
        posterPath = value.posterPath,
        releaseDate = value.releaseDate,
        voteAverage = value.voteAverage,
        voteCount = value.voteCount
    )
}