package com.kmkole86.plainandsimple.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kmkole86.movies.navigation.moviesRoute
import com.kmkole86.plainandsimple.home.navigation.HomeNavHost
import com.kmkole86.plainandsimple.home.navigation.TopDestinations
import com.kmkole86.screen2.navigation.screen2Route
import com.kmkole86.screen3.navigation.screen3Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val topBarTitle = rememberSaveable { (mutableStateOf("")) }
    val topBarNavigationVisibility = rememberSaveable { (mutableStateOf(false)) }


    HandleDestinationTitleAndNavigationIcon(
        navBackStackEntry?.destination?.route,
        title = { topBarTitle.value = it },
        navigationVisibility = { topBarNavigationVisibility.value = it })

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                modifier = modifier,
                title = topBarTitle,
            )
        },
        bottomBar = { HomeBottomBar(modifier = modifier, navController = navController) },
        content = { HomeNavHost(modifier = modifier.padding(it), navController = navController) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: State<String>
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = title.value
            )
        })
}

@Composable
fun HomeBottomBar(modifier: Modifier = Modifier, navController: NavController) {

    val navDestinations = listOf(
        TopDestinations.Movies,
        TopDestinations.Screen2,
        TopDestinations.Screen3,
    )
    NavigationBar(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        navDestinations.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = modifier,
                        imageVector = screen.iconVector,
                        contentDescription = null
                    )
                },
                label = { Text(modifier = modifier, text = stringResource(screen.resourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun HandleDestinationTitleAndNavigationIcon(
    route: String?,
    title: (String) -> Unit,
    navigationVisibility: (Boolean) -> Unit
) {
    when (route) {
        moviesRoute -> {
            navigationVisibility(false)
            title(stringResource(id = com.kmkole86.plainandsimple.R.string.app_name))
        }
        screen2Route -> {
            navigationVisibility(false)
            title(stringResource(id = com.kmkole86.plainandsimple.R.string.app_name))
        }
        screen3Route -> {
            navigationVisibility(false)
            title(stringResource(id = com.kmkole86.plainandsimple.R.string.app_name))
        }
        else -> {
            navigationVisibility(false)
            title(stringResource(id = com.kmkole86.plainandsimple.R.string.app_name))
        }
    }
}