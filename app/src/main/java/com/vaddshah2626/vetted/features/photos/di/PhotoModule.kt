package com.vaddshah2626.vetted.features.photos.di

import com.vaddshah2626.vetted.core.db.AppDatabase
import com.vaddshah2626.vetted.features.photos.data.PhotoRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val photoModule = module {
    single { get<AppDatabase>().photoDao() }
    singleOf(::PhotoRepository)
}