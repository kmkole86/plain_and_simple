package com.kmkole86.plainandsimple.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmkole86.domain.use_case.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val state = _state.asStateFlow()

    init {
        //todo if restored state (savedStateHandle) skip check
        viewModelScope.launch {
            getMoviesUseCase.get(1).collect {
                when (it) {
                    is GetMoviesUseCase.MoviesResult.Loading -> _state.value = HomeUiState.Loading
                    else -> _state.value = HomeUiState.Success
                }
            }
        }
    }
}

sealed interface HomeUiState {
    object Loading : HomeUiState
    object Success : HomeUiState
}