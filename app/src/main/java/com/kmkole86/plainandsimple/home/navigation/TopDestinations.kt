package com.kmkole86.plainandsimple.home.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.kmkole86.movies.navigation.moviesRoute
import com.kmkole86.plainandsimple.R
import com.kmkole86.screen2.navigation.screen2Route
import com.kmkole86.screen3.navigation.screen3Route

sealed class TopDestinations(
    val route: String,
    @StringRes val resourceId: Int,
    val iconVector: ImageVector
) {
    object Movies : TopDestinations(
        route = moviesRoute,
        resourceId = R.string.home_bottom_navigation_bar_feed,
        iconVector = Icons.Filled.Home
    )

    object Screen2 : TopDestinations(
        route = screen2Route,
        resourceId = R.string.home_bottom_navigation_bar_favourites,
        iconVector = Icons.Filled.Favorite
    )

    object Screen3 : TopDestinations(
        route = screen3Route,
        resourceId = R.string.home_bottom_navigation_bar_profile,
        iconVector = Icons.Filled.AccountBox
    )
}