package com.kmkole86.domain.use_case

import com.kmkole86.domain.entity.Page
import com.kmkole86.domain.repository.MovieRepository
import com.kmkole86.domain.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class GetMoviesUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val movieRepository: MovieRepository
) {

    operator fun invoke(pageOrdinal: Int): Flow<MoviesResult> =
        flow<MoviesResult> {
            when (val result = movieRepository.getMovies(pageOrdinal)) {
                is Result.Error -> emit(MoviesResult.Error)
                is Result.Success -> emit(MoviesResult.Success(result.data))
            }
        }.onStart { emit(MoviesResult.Loading) }.flowOn(dispatcher)


    sealed interface MoviesResult {
        object Loading : MoviesResult
        data class Success(val page: Page) : MoviesResult
        object Error : MoviesResult
    }

    sealed interface Error {
        object GenericError : Error
        //TODO add meaningful errors
    }
}