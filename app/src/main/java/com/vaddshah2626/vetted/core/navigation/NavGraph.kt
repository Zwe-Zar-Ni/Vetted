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
import com.vaddshah2626.vetted.features.categories.ui.screens.CategoriesScreen
import com.vaddshah2626.vetted.features.onboarding.screens.WelcomeScreen
import com.vaddshah2626.vetted.features.wishlist.ui.screens.WishlistCreateScreen
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
            startDestination = NavRoutes.WelcomeRoute,
            modifier = Modifier.padding(innerPadding)
        ) {

            // ? Tab screens
            navigation<NavRoutes.TabRoutes>(startDestination = NavRoutes.WishlistRoute) {
                composable<NavRoutes.WishlistRoute> {
                    WishlistScreen(
                        onCreateClick = {
                            navController.navigate(NavRoutes.WishlistCreateRoute)
                        }
                    )
                }
            }

            // ? Onboarding screens
            composable<NavRoutes.WelcomeRoute> {
                WelcomeScreen(
                    onContinue = {
                        navController.navigate(NavRoutes.CategoriesListRoute)
                    }
                )
            }

            // ? Other Routes
            composable<NavRoutes.CategoriesListRoute> {
                CategoriesScreen(
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
        }
    }
}

