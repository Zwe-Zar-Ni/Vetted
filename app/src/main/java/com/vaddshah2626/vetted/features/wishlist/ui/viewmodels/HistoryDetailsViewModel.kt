package com.vaddshah2626.vetted.features.wishlist.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaddshah2626.vetted.features.photos.data.Photo
import com.vaddshah2626.vetted.features.photos.data.PhotoRepository
import com.vaddshah2626.vetted.features.sources.data.Source
import com.vaddshah2626.vetted.features.sources.data.SourceRepository
import com.vaddshah2626.vetted.features.wishlist.data.ItemStatus
import com.vaddshah2626.vetted.features.wishlist.data.Wishlist
import com.vaddshah2626.vetted.features.wishlist.data.WishlistRepository
import com.vaddshah2626.vetted.features.wishlist.data.WishlistWithDetails
import com.vaddshah2626.vetted.features.wishlist.utils.deleteImageFromInternalStorage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HistoryDetailsViewModel(
    private val repository: WishlistRepository,
    itemId: Int,
    private val photoRepository: PhotoRepository
) : ViewModel() {


    @OptIn(ExperimentalCoroutinesApi::class)
    val wishlist: StateFlow<WishlistWithDetails?> =
        repository.getWishlistDetails(itemId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    suspend fun updateWishlist(wishlist: Wishlist) {
        repository.updateWishlist(wishlist)
    }

    suspend fun retireWishlist(wishlist : Wishlist) {
        repository.updateWishlist(
            wishlist.copy(
                retiredAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun addPhoto(photo: Photo) {
        photoRepository.insertPhoto(photo)
    }

    suspend fun deletePhoto(photo: Photo) {
        val deleted = deleteImageFromInternalStorage(photo.fileUri)
        if (deleted) {
            photoRepository.deletePhoto(photo)
        }
    }
}
