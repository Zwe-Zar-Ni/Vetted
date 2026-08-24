package com.vaddshah2626.vetted.features.wishlist.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaddshah2626.vetted.features.sources.data.Source
import com.vaddshah2626.vetted.features.sources.data.SourceRepository
import com.vaddshah2626.vetted.features.wishlist.data.WishlistRepository
import com.vaddshah2626.vetted.features.wishlist.data.WishlistWithDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class WishlistDetailsViewModel(
    private val repository: WishlistRepository,
    wishlistId: Int,
    private val sourceRepository: SourceRepository,
) : ViewModel() {

    private val refreshTrigger = MutableStateFlow(System.currentTimeMillis())

    @OptIn(ExperimentalCoroutinesApi::class)
    val wishlist: StateFlow<WishlistWithDetails?> = refreshTrigger
        .flatMapLatest {
            repository.getWishlistDetails(wishlistId)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun refreshWishlist() {
        refreshTrigger.value = System.currentTimeMillis()
    }

    suspend fun addSource(source: Source) {
        sourceRepository.insertSource(
            source
        )
        refreshWishlist()
    }

    suspend fun updateSource(source : Source) {
        sourceRepository.updateSource(source)
    }

    suspend fun deleteSource(source : Source) {
        sourceRepository.deleteSource(source)
    }
}
