package com.vaddshah2626.vetted.features.categories.di

import com.vaddshah2626.vetted.core.db.AppDatabase
import com.vaddshah2626.vetted.features.categories.data.CategoryRepository
import com.vaddshah2626.vetted.features.categories.ui.CategoryViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val categoryModule = module {
    single { get<AppDatabase>().categoryDao() }
    singleOf(::CategoryRepository)
    viewModelOf(::CategoryViewModel)
}