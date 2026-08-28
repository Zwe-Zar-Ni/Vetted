package com.vaddshah2626.vetted.features.history.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaddshah2626.vetted.features.history.data.HistoryRepository
import com.vaddshah2626.vetted.features.wishlist.data.WishlistRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {

    val history = repository.history.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

}