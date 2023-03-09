package com.kmkole86.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MovieList(
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    moviesState: MoviesState,
    onMovieClicked: (Int) -> Unit,
    onRetryClicked: () -> Unit,
) {

    LazyColumn(
        state = lazyListState, modifier = modifier.fillMaxSize()
    ) {
        items(
            moviesState.listItems.size,
            key = { index -> moviesState.listItems[index].key },
            itemContent = { index ->
                when (val item = moviesState.listItems[index]) {
                    MovieListModel.LoadingItem -> {
                        LoadingListItem(modifier = modifier)
                    }

                    is MovieListModel.MovieItem -> {
                        MovieListItem(
                            movie = item.data,
                            onItemClicked = onMovieClicked
                        )
                    }

                    MovieListModel.ErrorItem -> {
                        ErrorListItem(modifier = modifier, onRetryClicked = onRetryClicked)
                    }
                }
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListItem(
    movie: MovieModel,
    onItemClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        onClick = {
            onItemClicked(movie.id)
        },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            text = movie.title,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
@Preview
fun LoadingListItem(modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
            text = "Loading",
            maxLines = 1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        LinearProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
        )
    }
}

@Composable
fun ErrorListItem(onRetryClicked: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Column(modifier = modifier) {
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
                text = "Something went wrong...",
                maxLines = 1,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                onClick = { onRetryClicked() }) {
                Text(text = "Retry")
            }
        }
    }
}

@Composable
fun LazyListState.OnBottomReached(
    onBottomReached: () -> Unit
) {

    val shouldLoadMore = remember {
        derivedStateOf {

            // get last visible item
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf false
            // list is empty
            // return false here if loadMore should not be invoked if the list is empty

            // Check if last visible item is the last item in the list
            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect {
                // if should load more, then invoke loadMore
                if (it) {
                    onBottomReached()
                }
            }
    }
}

@Immutable
sealed class MovieListModel {
    abstract val key: Int

    data class MovieItem(val data: MovieModel) : MovieListModel() {
        override val key: Int
            get() = data.id
    }

    object LoadingItem : MovieListModel() {
        override val key: Int
            get() = -1
    }

    object ErrorItem : MovieListModel() {
        override val key: Int
            get() = -2
    }
}

data class MovieModel(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Float,
    val voteCount: Int
)