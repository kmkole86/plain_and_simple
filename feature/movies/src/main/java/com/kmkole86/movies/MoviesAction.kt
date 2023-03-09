package com.kmkole86.movies

import com.kmkole86.domain.use_case.GetMoviesUseCase

sealed interface MoviesAction {

    data class OnNextPageResult(val payload: GetMoviesUseCase.MoviesResult) : MoviesAction
}