package com.example.myhouseworkplanner.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myhouseworkplanner.presentation.screens.homeScreen.HomeScreen
import com.example.myhouseworkplanner.presentation.screens.roomScreen.RoomDetailScreen


@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {

        composable<HomeRoute> {
            HomeScreen(
                onNavigateToRoom = { roomId ->
                    navController.navigate(RoomDetailRoute(roomId = roomId))
                }
            )
        }

        composable<RoomDetailRoute> {
            RoomDetailScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}