package com.vaddshah2626.vetted.features.analytics.di

import com.vaddshah2626.vetted.core.db.AppDatabase
import com.vaddshah2626.vetted.features.analytics.data.AnalyticsRepository
import com.vaddshah2626.vetted.features.analytics.ui.viewmodels.AnalyticsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val analyticsModule = module {
    single { get<AppDatabase>().analyticsDao() }
    singleOf(::AnalyticsRepository)
    viewModelOf(::AnalyticsViewModel)
}