package com.kmkole86.movies

import androidx.annotation.MainThread
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmkole86.domain.use_case.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val INITIAL_PAGE_ORDINAL = 1
private const val RESTORED_STATE_KEY = "restored_state_key"
private const val LAST_PAGE_KEY = "last_page_key"

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _stateObservable: MutableStateFlow<MoviesState> = MutableStateFlow(
        MoviesState.Ok(
            data = persistentListOf(), listItems = persistentListOf(), lastShownPageOrdinal = 0
        )
    )
    val stateObservable: StateFlow<MoviesState> = _stateObservable.asStateFlow()

    init {
//        savedStateHandle.get<Bundle?>(RESTORED_STATE_KEY)?.let {
//            //todo restore old state
//            //todo implement getting the range of pages
//            //todo restore scroll position
//        } ?: //todo init new state
//
//        savedStateHandle.setSavedStateProvider(RESTORED_STATE_KEY) {
//            Bundle().apply {
//                putInt(LAST_PAGE_KEY, _stateObservable.value.lastShownPageOrdinal)
//            }
//        }

        getPage(INITIAL_PAGE_ORDINAL)
    }

    private fun onEvent(event: MoviesEvent) {
        when (event) {
            MoviesEvent.OnBottomOfListReached -> {
                if (stateObservable.value is MoviesState.Ok)
                    getPage(stateObservable.value.lastShownPageOrdinal + 1)
            }

            MoviesEvent.OnRetryClicked -> {
                if (stateObservable.value is MoviesState.Error)
                    getPage(stateObservable.value.lastShownPageOrdinal)
            }

            is MoviesEvent.OnMovieClicked -> {
                //todo implement
            }
        }
    }

    @MainThread
    private fun onAction(action: MoviesAction) {
        val currentState: MoviesState = _stateObservable.value
        val newState: MoviesState =
            MoviesReducer.getReducerForAction(action).reduce(action, currentState)
        _stateObservable.update { newState }
    }

    private fun getPage(pageOrdinal: Int) {
        viewModelScope.launch {
            getMoviesUseCase.get(pageOrdinal).collect {
                onAction(MoviesAction.OnNextPageResult(it))
            }
        }
    }

    internal fun onBottomOfListReached() {
        onEvent(MoviesEvent.OnBottomOfListReached)
    }

    internal fun onRetryClicked() {
        onEvent(MoviesEvent.OnRetryClicked)
    }

    internal fun onMovieClicked(movieId: Int) {
        onEvent(MoviesEvent.OnMovieClicked(movieId))
    }
}