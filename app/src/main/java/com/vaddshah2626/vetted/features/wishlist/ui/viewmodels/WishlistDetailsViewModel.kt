package com.vaddshah2626.vetted.features.wishlist.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaddshah2626.vetted.features.wishlist.data.WishlistRepository
import com.vaddshah2626.vetted.features.wishlist.data.WishlistWithDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class WishlistDetailsViewModel(private val repository: WishlistRepository , wishlistId: Int) : ViewModel() {
    val wishlist: StateFlow<WishlistWithDetails?> = repository.getWishlistDetails(wishlistId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
}
