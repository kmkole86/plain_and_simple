package com.kmkole86.plainandsimple.home.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.kmkole86.movies.navigation.moviesRoute
import com.kmkole86.movies.navigation.moviesScreen
import com.kmkole86.screen2.navigation.screen2
import com.kmkole86.screen3.navigation.screen3

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = moviesRoute
) {
    AnimatedNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        moviesScreen()
        screen2()
        screen3()
    }
}