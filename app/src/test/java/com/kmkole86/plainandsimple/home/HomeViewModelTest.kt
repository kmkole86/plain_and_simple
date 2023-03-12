package com.kmkole86.plainandsimple.home

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.kmkole86.domain.use_case.GetMoviesUseCase
import com.kmkole86.plainandsimple.home.common.DispatcherMainRule
import com.kmkole86.plainandsimple.home.models.createPageDummyData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = DispatcherMainRule()

    @Mock
    private lateinit var getMoviesUseCase: GetMoviesUseCase

    @Test
    fun `viewModel delegates movie getting to useCase`() = runTest {
        `when`(getMoviesUseCase.get(anyInt())).thenReturn(emptyFlow<GetMoviesUseCase.MoviesResult>().onStart {
            emit(GetMoviesUseCase.MoviesResult.Loading)
        }.flowOn(mainDispatcherRule.testDispatcher))

        HomeViewModel(
            savedStateHandle = SavedStateHandle(),
            getMoviesUseCase = getMoviesUseCase
        )
        verify(getMoviesUseCase, times(1)).get(1)
    }

    @Test
    fun `viewModel emit success on movie fetch finished`() = runTest {
        val mutableFlow: MutableSharedFlow<GetMoviesUseCase.MoviesResult> = MutableSharedFlow()

        `when`(getMoviesUseCase.get(anyInt())).thenReturn(mutableFlow)

        val viewModel = HomeViewModel(
            savedStateHandle = SavedStateHandle(),
            getMoviesUseCase = getMoviesUseCase
        )

        mutableFlow.emit(GetMoviesUseCase.MoviesResult.Loading)
        assertThat(viewModel.state.value).isEqualTo(HomeUiState.Loading)

        mutableFlow.emit(GetMoviesUseCase.MoviesResult.Error)
        assertThat(viewModel.state.value).isEqualTo(HomeUiState.Success)

        mutableFlow.emit(
            GetMoviesUseCase.MoviesResult.Success(
                page = createPageDummyData(
                    pageOrdinal = 1
                )
            )
        )
        assertThat(viewModel.state.value).isEqualTo(HomeUiState.Success)
    }
}