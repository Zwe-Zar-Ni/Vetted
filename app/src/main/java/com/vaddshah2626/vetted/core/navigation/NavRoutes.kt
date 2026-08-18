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
}