package com.vaddshah2626.vetted.features.sources.di

import com.vaddshah2626.vetted.core.db.AppDatabase
import com.vaddshah2626.vetted.features.sources.data.SourceRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val sourceModule = module {
    single { get<AppDatabase>().sourceDao() }
    singleOf(::SourceRepository)
}