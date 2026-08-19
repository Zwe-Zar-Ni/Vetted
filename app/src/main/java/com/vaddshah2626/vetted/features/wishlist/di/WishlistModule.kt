package com.vaddshah2626.vetted.features.wishlist.di

import com.vaddshah2626.vetted.core.db.AppDatabase
import com.vaddshah2626.vetted.features.wishlist.data.WishlistRepository
import com.vaddshah2626.vetted.features.wishlist.ui.WishlistViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val wishlistModule = module {
    single { get<AppDatabase>().wishlistDao() }
    singleOf(::WishlistRepository)
    viewModelOf(::WishlistViewModel)
}