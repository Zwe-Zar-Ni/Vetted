package com.vaddshah2626.vetted.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vaddshah2626.vetted.features.categories.data.Category
import com.vaddshah2626.vetted.features.categories.data.CategoryDao
import com.vaddshah2626.vetted.features.wishlist.data.Wishlist
import com.vaddshah2626.vetted.features.wishlist.data.WishlistDao

@Database(entities = [Wishlist::class, Category::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    abstract fun wishlistDao(): WishlistDao
}