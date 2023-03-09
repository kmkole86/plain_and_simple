package com.kmkole86.movies

import com.kmkole86.domain.entity.Movie
import com.kmkole86.domain.use_case.GetMoviesUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

internal sealed class MoviesReducer<Action : MoviesAction> {

    abstract fun reduce(
        action: Action, state: MoviesState
    ): MoviesState

    companion object {
        fun <Action : MoviesAction> getReducerForAction(action: Action): MoviesReducer<Action> {

            @Suppress("UNCHECKED_CAST") return when (action as MoviesAction) {
                is MoviesAction.OnNextPageResult -> OnNextPageReducer
            } as MoviesReducer<Action>
        }
    }

    object OnNextPageReducer : MoviesReducer<MoviesAction.OnNextPageResult>() {
        override fun reduce(
            action: MoviesAction.OnNextPageResult, state: MoviesState
        ): MoviesState {
            return when (val result = action.payload) {
                GetMoviesUseCase.MoviesResult.Error -> {
                    MoviesState.Error(
                        data = state.data,
                        listItems = generateListItems(state.data, result),
                        lastShownPageOrdinal = state.lastShownPageOrdinal
                    )
                }

                GetMoviesUseCase.MoviesResult.Loading -> {
                    MoviesState.Loading(
                        data = state.data,
                        listItems = generateListItems(state.data, result),
                        lastShownPageOrdinal = state.lastShownPageOrdinal
                    )
                }

                is GetMoviesUseCase.MoviesResult.Success -> {
                    if (result.page.hasNext)
                        MoviesState.Ok(
                            data = (state.data + result.page.movies.map { mapToMovieModel(it) }).toImmutableList(),
                            listItems = generateListItems(state.data, result),
                            lastShownPageOrdinal = result.page.ordinal
                        )
                    else MoviesState.EndOfListReached(
                        data = (state.data + result.page.movies.map { mapToMovieModel(it) }).toImmutableList(),
                        listItems = generateListItems(state.data, result),
                        lastShownPageOrdinal = result.page.ordinal
                    )
                }
            }
        }

        private fun generateListItems(
            currentList: ImmutableList<MovieModel>,
            newPage: GetMoviesUseCase.MoviesResult
        ): ImmutableList<MovieListModel> {
            return when (newPage) {
                GetMoviesUseCase.MoviesResult.Error ->
                    currentList.map<MovieModel, MovieListModel> {
                        MovieListModel.MovieItem(
                            data = it
                        )
                    }.toMutableList().apply { add(MovieListModel.ErrorItem) }.toImmutableList()

                GetMoviesUseCase.MoviesResult.Loading -> currentList.map<MovieModel, MovieListModel> {
                    MovieListModel.MovieItem(
                        data = it
                    )
                }.toMutableList().apply { add(MovieListModel.LoadingItem) }.toImmutableList()

                is GetMoviesUseCase.MoviesResult.Success -> {
                    val newList =
                        newPage.page.movies.map { mapToMovieModel(it) }
                    (currentList + newList).map {
                        MovieListModel.MovieItem(
                            data = it
                        )
                    }.toImmutableList()
                }
            }
        }

        private fun mapToMovieModel(value: Movie): MovieModel {
            return MovieModel(
                id = value.id,
                title = value.title,
                overview = value.overview,
                posterPath = value.posterPath,
                releaseDate = value.releaseDate,
                voteAverage = value.voteAverage,
                voteCount = value.voteCount
            )
        }
    }
}