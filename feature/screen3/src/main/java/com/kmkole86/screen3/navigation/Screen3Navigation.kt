package com.kmkole86.screen3.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.kmkole86.screen3.Screen3Route

const val screen3Route = "screen3_route"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.screen3() {
    composable(route = screen3Route, enterTransition = {
        slideIntoContainer(
            AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700)
        )
    }, exitTransition = {
        slideOutOfContainer(
            AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700)
        )
    }) {
        Screen3Route()
    }
}