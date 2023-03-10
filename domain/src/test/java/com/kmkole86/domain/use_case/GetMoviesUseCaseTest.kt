package com.kmkole86.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.kmkole86.domain.entity.Movie
import com.kmkole86.domain.entity.Page
import com.kmkole86.domain.repository.MovieRepository
import com.kmkole86.domain.result.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class GetMoviesUseCaseTest {

    @Mock
    private lateinit var movieRepository: MovieRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = StandardTestDispatcher()
    private val pageOrdinal: Int = 1

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test use case delegates to repo`() = runTest(dispatcher) {
        GetMoviesUseCase(dispatcher = dispatcher, movieRepository).get(pageOrdinal).collect()
        advanceUntilIdle()

        verify(movieRepository, times(1)).getMovies(pageOrdinal)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test repository returns success`() = runTest(dispatcher) {
        val page = createPageDummyData(pageOrdinal = pageOrdinal)
        `when`(movieRepository.getMovies(pageOrdinal)).thenAnswer {
            Result.Success(data = page)
        }

        val results = GetMoviesUseCase(dispatcher = dispatcher, movieRepository).get(pageOrdinal)
            .toList()
        advanceUntilIdle()

        assertThat(results.size).isEqualTo(2)
        assertThat(results[0]).isInstanceOf(GetMoviesUseCase.MoviesResult.Loading::class.java)
        assertThat(results[1]).isInstanceOf(GetMoviesUseCase.MoviesResult.Success::class.java)
        assertThat((results[1] as GetMoviesUseCase.MoviesResult.Success).page).isSameInstanceAs(
            page
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test repository returns an error`() = runTest(dispatcher) {
        `when`(movieRepository.getMovies(pageOrdinal)).thenAnswer {
            Result.Error()
        }

        val results = GetMoviesUseCase(dispatcher = dispatcher, movieRepository).get(pageOrdinal)
            .toList()
        advanceUntilIdle()

        assertThat(results.size).isEqualTo(2)
        assertThat(results[0]).isInstanceOf(GetMoviesUseCase.MoviesResult.Loading::class.java)
        assertThat(results[1]).isInstanceOf(GetMoviesUseCase.MoviesResult.Error::class.java)
    }
}

private fun createPageDummyData(pageOrdinal: Int): Page {
    return Page(
        ordinal = pageOrdinal,
        movies = (1..20).map { createMovieDummyData(it) },
        hasNext = true
    )
}

fun createMovieDummyData(id: Int): Movie {
    return Movie(
        id = id,
        title = "title $id",
        overview = "overview $id",
        posterPath = "posterPath $id",
        releaseDate = "n/a",
        voteAverage = id.toFloat(),
        voteCount = id
    )
}