package com.vaddshah2626.vetted.features.wishlist.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaddshah2626.vetted.features.wishlist.data.AnalyticsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class AnalyticsUiState(
    val avgDaysInReady: Double? = null,
    val pipelineValue: Double = 0.0,
    val conversionRate: Double = 0.0,
    val isLoading: Boolean = true
)

class AnalyticsViewModel(private val repository: AnalyticsRepository) : ViewModel() {

    val uiState: StateFlow<AnalyticsUiState> = combine(
        repository.averageDaysInReady,
        repository.wishlistPipelineValue,
        repository.wishlistConversionRate
    ) { avgDays, pipelineValue, conversionRate ->
        AnalyticsUiState(
            // ?  Average time an item spends in `READY` status before moving to `PURCHASED`
            avgDaysInReady = avgDays,
            // ? Total monetary value required to clear all current items marked as `READY` to buy.
            pipelineValue = pipelineValue,
            // ? Percentage of wishlisted items that actually get bought versus items that end up canceled or permanently stalled.
            conversionRate = conversionRate,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AnalyticsUiState(isLoading = true)
    )
}