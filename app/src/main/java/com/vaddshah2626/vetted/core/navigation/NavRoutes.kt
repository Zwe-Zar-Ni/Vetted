package com.vaddshah2626.vetted.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavRoutes {

    // ? Onboarding Routes
    @Serializable
    data object WelcomeRoute : NavRoutes()


    // ? Tab routes
    @Serializable
    object TabRoutes : NavRoutes()

    @Serializable
    object WishlistRoute : NavRoutes()

    // ? Other screens
    @Serializable
    object CategoriesListRoute : NavRoutes()

    @Serializable
    object WishlistCreateRoute : NavRoutes()

    @Serializable
    data class WishlistDetailsRoute(val wishlistId : Int) : NavRoutes()

    @Serializable
    object HistoryRoute : NavRoutes()

    @Serializable
    data class HistoryDetailsRoute(val itemId : Int) : NavRoutes()
}