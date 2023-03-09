package com.kmkole86.movies

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun MoviesRoute(
    modifier: Modifier = Modifier,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    val moviesState by viewModel.stateObservable.collectAsStateWithLifecycle()

    MoviesScreen(
        modifier = modifier,
        moviesState = moviesState,
        onMovieClicked = viewModel::onMovieClicked,
        onBottomOfListReached = viewModel::onBottomOfListReached,
        onRetryClicked = viewModel::onRetryClicked
    )
}

@Composable
internal fun MoviesScreen(
    modifier: Modifier = Modifier,
    moviesState: MoviesState,
    onMovieClicked: (Int) -> Unit,
    onBottomOfListReached: () -> Unit,
    onRetryClicked: () -> Unit
) {

    val listState = rememberLazyListState()

    listState.OnBottomReached {
        onBottomOfListReached()
    }

    MovieList(
        lazyListState = listState,
        moviesState = moviesState,
        onMovieClicked = onMovieClicked,
        onRetryClicked = onRetryClicked,
        modifier = modifier
    )
}