package com.vaddshah2626.vetted.core.di

import androidx.room.Room
import com.vaddshah2626.vetted.core.db.AppDatabase
import org.koin.dsl.module

val appModule = module {
    // Provide Room Database
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

}