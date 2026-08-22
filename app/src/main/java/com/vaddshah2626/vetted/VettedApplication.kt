package com.vaddshah2626.vetted

import android.app.Application
import com.vaddshah2626.vetted.core.di.appModule
import com.vaddshah2626.vetted.features.categories.di.categoryModule
import com.vaddshah2626.vetted.features.photos.di.photoModule
import com.vaddshah2626.vetted.features.wishlist.di.wishlistModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class VettedApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@VettedApplication)
            modules(appModule, wishlistModule, categoryModule , photoModule)
        }
    }
}