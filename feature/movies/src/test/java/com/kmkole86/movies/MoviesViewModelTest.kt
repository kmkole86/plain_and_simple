package com.kmkole86.movies

import androidx.lifecycle.SavedStateHandle
import com.kmkole86.domain.use_case.GetMoviesUseCase
import com.kmkole86.movies.common.DispatcherMainRule
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    @get:Rule
    val mainDispatcherRule = DispatcherMainRule()

    @Mock
    private lateinit var getMoviesUseCase: GetMoviesUseCase

    @Test
    fun `viewModel delegates movie getting to useCase`() = runTest {
        Mockito.`when`(getMoviesUseCase.get(anyInt()))
            .thenReturn(emptyFlow<GetMoviesUseCase.MoviesResult>().onStart {
            emit(GetMoviesUseCase.MoviesResult.Loading)
        }.flowOn(mainDispatcherRule.testDispatcher))

        MoviesViewModel(
            savedStateHandle = SavedStateHandle(),
            getMoviesUseCase = getMoviesUseCase
        )
        verify(getMoviesUseCase, times(1)).get(1)
    }
}