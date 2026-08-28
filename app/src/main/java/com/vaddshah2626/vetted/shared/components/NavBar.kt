package com.vaddshah2626.vetted.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vaddshah2626.vetted.core.navigation.NavRoutes

data class NavItem(
    val title: String,
    val icon: ImageVector,
    val route: NavRoutes
)

private val navItems = listOf(
    NavItem(title = "Wishlist", icon = Icons.Default.Checklist, route = NavRoutes.WishlistRoute),
    NavItem(title = "History", icon = Icons.Default.History, route = NavRoutes.HistoryRoute),
    NavItem(title = "Analytics", icon = Icons.Default.BarChart, route = NavRoutes.AnalyticsRoute),
    NavItem(title = "Profile", icon = Icons.Default.Person, route = NavRoutes.WelcomeRoute),
)

@Composable
fun NavBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    fun isCurrentRoute(item: NavItem): Boolean {
        return currentDestination?.hierarchy?.any {
            it.hasRoute(item.route::class)
        } == true
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        navItems.forEach { item ->
            NavigationBarItem(
                selected = isCurrentRoute(item),
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo<NavRoutes.WishlistRoute> {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
//                label = { Text(item.title, style = MaterialTheme.typography.bodyMedium) },
                label = {},
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = MaterialTheme.colorScheme.primary,
//                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = Color.Transparent,
                )
            )
        }
    }
}
