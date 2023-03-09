package com.kmkole86.movies

import kotlinx.collections.immutable.ImmutableList

sealed class MoviesState {

    abstract val data: ImmutableList<MovieModel>
    abstract val listItems: ImmutableList<MovieListModel>
    abstract val lastShownPageOrdinal: Int

    data class Loading(
        override val data: ImmutableList<MovieModel>,
        override val listItems: ImmutableList<MovieListModel>,
        override val lastShownPageOrdinal: Int
    ) : MoviesState()

    data class Ok(
        override val data: ImmutableList<MovieModel>,
        override val listItems: ImmutableList<MovieListModel>,
        override val lastShownPageOrdinal: Int
    ) : MoviesState()

    data class EndOfListReached(
        override val data: ImmutableList<MovieModel>,
        override val listItems: ImmutableList<MovieListModel>,
        override val lastShownPageOrdinal: Int
    ) : MoviesState()

    data class Error(
        override val data: ImmutableList<MovieModel>,
        override val listItems: ImmutableList<MovieListModel>,
        override val lastShownPageOrdinal: Int
    ) : MoviesState()
}