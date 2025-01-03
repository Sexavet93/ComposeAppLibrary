package com.compose.composelibrary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Composable
fun NavigationBarScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            var selectedItem by remember { mutableStateOf(0) }
            NavigationBar {
                bottomNavigationItems().forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = index == selectedItem,
                        onClick = {
                            selectedItem = index
                            when(val route = item.route) {
                                is ScreenRoute.HomeRoute -> navController.navigate(route.getRoute("data"))
                                is ScreenRoute.SearchRoute -> navController.navigate(route.getRoute(10.0))
                                is ScreenRoute.ProfileRoute -> navController.navigate(route.getRoute(10))
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHostComponent(navController = navController, paddingValues = paddingValues)
    }
}

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : ScreenRoute
)

sealed class ScreenRoute {
    object HomeRoute : ScreenRoute() {
        fun getRoute(data: String): Route {
            return Home(data)
        }
    }

    object SearchRoute : ScreenRoute() {
        fun getRoute(data: Double): Route {
            return Search(data)
        }
    }

    object ProfileRoute : ScreenRoute() {
        fun getRoute(data: Int): Route {
            return Profile(data)
        }
    }
}

fun bottomNavigationItems() : List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            label = "Home",
            icon = Icons.Filled.Home,
            route = ScreenRoute.HomeRoute
        ),
        BottomNavigationItem(
            label = "Search",
            icon = Icons.Filled.Search,
            route = ScreenRoute.SearchRoute
        ),
        BottomNavigationItem(
            label = "Profile",
            icon = Icons.Filled.AccountCircle,
            route = ScreenRoute.ProfileRoute
        ),
    )
}

abstract class Route

@Serializable
data class Home(val data: String): Route()
@Serializable
data class Search(val data: Double): Route()
@Serializable
data class Profile(val data: Int): Route()


@Composable
fun NavHostComponent(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Home(""),
        modifier = Modifier.padding(paddingValues = paddingValues)) {
        composable<Home> { backStackEntry ->
            val data : Home = backStackEntry.toRoute()
            HomeScreen(data)
        }
        composable<Search> { backStackEntry ->
            val data : Search = backStackEntry.toRoute()
            SearchScreen(data)
        }
        composable<Profile> { backStackEntry ->
            val data : Profile = backStackEntry.toRoute()
            ProfileScreen(data)
        }
    }
}

@Composable
fun HomeScreen(data : Home) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            ChannelHandler.invokeChannel.invokeMethod("flutterMethod", data.data)
        }) {

        }
    }
}

@Composable
fun SearchScreen(data : Search) {
    Text(text = data.data.toString())
}

@Composable
fun ProfileScreen(data : Profile) {
    Text(text = data.data.toString())
}