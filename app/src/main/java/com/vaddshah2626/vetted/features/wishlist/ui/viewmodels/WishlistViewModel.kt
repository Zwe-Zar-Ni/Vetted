package com.vaddshah2626.vetted.features.wishlist.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaddshah2626.vetted.features.wishlist.data.WishlistRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class WishlistViewModel(private val repository: WishlistRepository) : ViewModel() {

    var refreshed by mutableStateOf(false)
        private set

    val wishlists = repository.allWishlists.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val categories = repository.categories.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val currentMonthSpending = repository.currentMonthSpending.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )

    suspend fun checkWishlistsStatus() {
        if (refreshed) return
        println("Refreshing . . .")
        repository.checkWishlistsStatuses()
        refreshed = true
    }
}