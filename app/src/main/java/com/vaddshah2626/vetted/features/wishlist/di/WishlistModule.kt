package com.vaddshah2626.vetted.features.wishlist.di

import com.vaddshah2626.vetted.core.db.AppDatabase
import com.vaddshah2626.vetted.features.analytics.data.AnalyticsRepository
import com.vaddshah2626.vetted.features.wishlist.data.WishlistRepository
import com.vaddshah2626.vetted.features.analytics.ui.viewmodels.AnalyticsViewModel
import com.vaddshah2626.vetted.features.wishlist.ui.viewmodels.WishlistCreateViewModel
import com.vaddshah2626.vetted.features.wishlist.ui.viewmodels.WishlistDetailsViewModel
import com.vaddshah2626.vetted.features.wishlist.ui.viewmodels.WishlistViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val wishlistModule = module {
    single { get<AppDatabase>().wishlistDao() }
    singleOf(::WishlistRepository)
    viewModelOf(::WishlistViewModel)
    viewModelOf(::WishlistCreateViewModel)
    viewModel { (wishlistId: Int) ->
        WishlistDetailsViewModel(
            get(),
            wishlistId = wishlistId,
            get(),
            get(),
            androidContext()
        )
    }
    singleOf(::AnalyticsRepository)
    viewModelOf(::AnalyticsViewModel)
}