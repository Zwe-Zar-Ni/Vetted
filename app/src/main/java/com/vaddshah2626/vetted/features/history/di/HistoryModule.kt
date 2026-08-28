package com.vaddshah2626.vetted.features.history.di

import com.vaddshah2626.vetted.core.db.AppDatabase
import com.vaddshah2626.vetted.features.history.data.HistoryRepository
import com.vaddshah2626.vetted.features.history.ui.viewmodels.HistoryDetailsViewModel
import com.vaddshah2626.vetted.features.history.ui.viewmodels.HistoryViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val historyModule = module {
    single { get<AppDatabase>().historyDao() }
    singleOf(::HistoryRepository)
    viewModelOf(::HistoryViewModel)
    viewModel { (itemId: Int) ->
        HistoryDetailsViewModel(
            repository = get(),
            itemId = itemId,
            photoRepository = get()
        )
    }
}