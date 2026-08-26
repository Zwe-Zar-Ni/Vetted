package com.vaddshah2626.vetted.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.vaddshah2626.vetted.features.onboarding.screens.WelcomeScreen
import com.vaddshah2626.vetted.features.wishlist.ui.screens.HistoryDetailsScreen
import com.vaddshah2626.vetted.features.wishlist.ui.screens.HistoryScreen
import com.vaddshah2626.vetted.features.wishlist.ui.screens.WishlistCreateScreen
import com.vaddshah2626.vetted.features.wishlist.ui.screens.WishlistDetailsScreen
import com.vaddshah2626.vetted.features.wishlist.ui.screens.WishlistScreen
import com.vaddshah2626.vetted.shared.components.NavBar

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isInTabGraph = currentDestination?.hierarchy?.any {
        it.hasRoute<NavRoutes.TabRoutes>()
    } == true

    Scaffold(bottomBar = {
        if (isInTabGraph) {
            NavBar(navController)
        }
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavRoutes.TabRoutes,
            modifier = Modifier.padding(innerPadding)
        ) {

            // ? Tab screens
            navigation<NavRoutes.TabRoutes>(startDestination = NavRoutes.WishlistRoute) {
                composable<NavRoutes.WishlistRoute> {
                    WishlistScreen(
                        onCreateClick = {
                            navController.navigate(NavRoutes.WishlistCreateRoute)
                        },
                        onWishlistClick = { wishlistId ->
                            navController.navigate(NavRoutes.WishlistDetailsRoute(wishlistId))
                        }
                    )
                }
                composable<NavRoutes.HistoryRoute> {
                    HistoryScreen(
                        onItemClick = { itemId ->
                            navController.navigate(NavRoutes.HistoryDetailsRoute(itemId))
                        }
                    )
                }
            }

            // ? Onboarding screens
            composable<NavRoutes.WelcomeRoute> {
                WelcomeScreen(
                    onContinue = {
                        navController.navigate(NavRoutes.TabRoutes) {
                            popUpTo(NavRoutes.TabRoutes) { inclusive = true }
                        }
                    }
                )
            }

            composable<NavRoutes.WishlistCreateRoute> {
                WishlistCreateScreen(
                    onNavigateBack = {
                        navController.navigate(NavRoutes.TabRoutes) {
                            popUpTo(NavRoutes.TabRoutes) { inclusive = true }
                        }
                    }
                )
            }
            composable<NavRoutes.WishlistDetailsRoute> { backStackEntry ->
                val parameters = backStackEntry.toRoute<NavRoutes.WishlistDetailsRoute>()
                WishlistDetailsScreen(
                    wishlistId = parameters.wishlistId,
                    onNavigateBack = {
                        navController.navigate(NavRoutes.TabRoutes) {
                            popUpTo(NavRoutes.TabRoutes) { inclusive = true }
                        }
                    }
                )
            }
            composable<NavRoutes.HistoryDetailsRoute> { backStackEntry ->
                val parameters = backStackEntry.toRoute<NavRoutes.HistoryDetailsRoute>()
                HistoryDetailsScreen(
                    itemId = parameters.itemId,
                    onNavigateBack = {
                        navController.navigate(NavRoutes.HistoryRoute) {
                            popUpTo(NavRoutes.TabRoutes) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

