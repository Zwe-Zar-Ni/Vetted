package com.vaddshah2626.vetted.features.history.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaddshah2626.vetted.core.models.WishlistWithDetails
import com.vaddshah2626.vetted.features.history.data.HistoryRepository
import com.vaddshah2626.vetted.features.photos.data.Photo
import com.vaddshah2626.vetted.features.photos.data.PhotoRepository
import com.vaddshah2626.vetted.features.wishlist.data.Wishlist
import com.vaddshah2626.vetted.features.wishlist.utils.deleteImageFromInternalStorage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HistoryDetailsViewModel(
    private val repository: HistoryRepository,
    itemId: Int,
    private val photoRepository: PhotoRepository,
) : ViewModel() {


    @OptIn(ExperimentalCoroutinesApi::class)
    val history: StateFlow<WishlistWithDetails?> =
        repository.getItemDetails(itemId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    suspend fun retireWishlist(wishlist: Wishlist) {
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
