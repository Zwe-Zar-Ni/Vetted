package com.vaddshah2626.vetted.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vaddshah2626.vetted.features.analytics.data.AnalyticsDao
import com.vaddshah2626.vetted.features.categories.data.Category
import com.vaddshah2626.vetted.features.categories.data.CategoryDao
import com.vaddshah2626.vetted.features.history.data.HistoryDao
import com.vaddshah2626.vetted.features.photos.data.Photo
import com.vaddshah2626.vetted.features.photos.data.PhotoDao
import com.vaddshah2626.vetted.features.sources.data.Source
import com.vaddshah2626.vetted.features.sources.data.SourceDao
import com.vaddshah2626.vetted.features.wishlist.data.Wishlist
import com.vaddshah2626.vetted.features.wishlist.data.WishlistDao

@Database(entities = [Wishlist::class, Category::class, Photo::class, Source::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    abstract fun wishlistDao(): WishlistDao

    abstract fun photoDao(): PhotoDao

    abstract fun sourceDao(): SourceDao

    abstract fun historyDao(): HistoryDao

    abstract fun analyticsDao(): AnalyticsDao
}