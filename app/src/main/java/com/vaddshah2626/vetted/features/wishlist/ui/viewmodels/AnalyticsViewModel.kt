package com.vaddshah2626.vetted.features.wishlist.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaddshah2626.vetted.features.wishlist.data.AnalyticsRepository
import com.vaddshah2626.vetted.features.wishlist.model.CategoryBreakdownDto
import com.vaddshah2626.vetted.features.wishlist.model.DesireConversionDto
import com.vaddshah2626.vetted.features.wishlist.model.MonthlySpendingDto
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class AnalyticsViewModel(private val repository: AnalyticsRepository) : ViewModel() {

    // ?  Average time an item spends in `READY` status before moving to `PURCHASED`
    val avgDaysInReady: StateFlow<Double?> = repository.getAverageDaysInReady()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // ? Total monetary value required to clear all current items marked as `READY` to buy.
    val pipelineValue: StateFlow<Double> = repository.getWishlistPipelineValue()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    // ? Percentage of wishlisted items that actually get bought versus items that end up canceled or permanently stalled.
    val conversionRate: StateFlow<Double> = repository.getWishlistConversionRate()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    // ? which desire ratings (1–10) actually result in purchases versus items that sit untouched or get deleted.
    val desireConversions: StateFlow<List<DesireConversionDto>> = repository.getDesireRatingConversion()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ? Visual spending distribution showing where most of the money and item count goes
    val categoryBreakdown: StateFlow<List<CategoryBreakdownDto>> = repository.getCategoryBreakdown()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ? Total amount spent on items month-over-month.
    val monthlySpendingTrend: StateFlow<List<MonthlySpendingDto>> = repository.getMonthlySpendingTrend()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}