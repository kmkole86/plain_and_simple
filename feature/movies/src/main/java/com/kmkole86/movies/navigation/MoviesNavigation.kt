package com.kmkole86.movies.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.kmkole86.movies.MoviesRoute

const val moviesRoute = "movies_route"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.moviesScreen() {
    composable(route = moviesRoute, enterTransition = {
        slideIntoContainer(
            AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700)
        )
    }, exitTransition = {
        slideOutOfContainer(
            AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700)
        )
    }) {
        MoviesRoute()
    }
}