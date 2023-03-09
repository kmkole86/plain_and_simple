package com.kmkole86.screen2.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.kmkole86.screen2.Screen2Route

const val screen2Route = "screen2_route"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.screen2() {
    composable(route = screen2Route, enterTransition = {
        slideIntoContainer(
            AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700)
        )
    }, exitTransition = {
        slideOutOfContainer(
            AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700)
        )
    }) {
        Screen2Route()
    }
}