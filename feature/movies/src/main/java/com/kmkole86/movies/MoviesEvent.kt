package com.kmkole86.movies

sealed class MoviesEvent {
    object OnBottomOfListReached : MoviesEvent()
    object OnRetryClicked : MoviesEvent()
    data class OnMovieClicked(val movieId: Int) : MoviesEvent()
}
