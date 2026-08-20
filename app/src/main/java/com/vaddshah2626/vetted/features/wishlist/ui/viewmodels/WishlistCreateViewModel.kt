package com.vaddshah2626.vetted.features.wishlist.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaddshah2626.vetted.features.wishlist.data.Wishlist
import com.vaddshah2626.vetted.features.wishlist.data.WishlistRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class WishlistFormState(
    val title: String = "",
    val titleError: String? = null,
    val categoryId: Int? = null,
    val categoryError: String? = null,
    val desireRating: Int = 3,
    val minTargetPrice: String = "",
    val maxTargetPrice: String = "",
    val variationsNote: String = "",
    val prePurchaseNote: String = "",
    val isSubmitting: Boolean = false
)

class WishlistCreateViewModel(
    private val repository: WishlistRepository
) : ViewModel() {

    val categories = repository.categories.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    var formState by mutableStateOf(WishlistFormState())
        private set

    // Simple event handlers for UI state updates
    fun onTitleChanged(newTitle: String) {
        formState = formState.copy(title = newTitle, titleError = null)
    }

    fun onCategorySelected(id: Int) {
        formState = formState.copy(categoryId = id, categoryError = null)
    }

    fun onDesireRatingChanged(rating: Int) {
        formState = formState.copy(desireRating = rating)
    }

    fun onMinPriceChanged(price: String) {
        formState = formState.copy(minTargetPrice = price)
    }

    fun onMaxPriceChanged(price: String) {
        formState = formState.copy(maxTargetPrice = price)
    }

    fun onVariationsNotesChanged(note: String) {
        formState = formState.copy(variationsNote = note)
    }

    fun onPrePurchaseNoteChanged(note: String) {
        formState = formState.copy(prePurchaseNote = note)
    }

    // Submit handler with inline validation
    fun saveWishlistItem(onSuccess: () -> Unit) {
        val hasTitleError = formState.title.isBlank()
        val hasCategoryError = formState.categoryId == null

        if (hasTitleError || hasCategoryError) {
            formState = formState.copy(
                titleError = if (hasTitleError) "Title is required" else null,
                categoryError = if (hasCategoryError) "Please select a category" else null
            )
            return
        }

        viewModelScope.launch {
            formState = formState.copy(isSubmitting = true)

            val newItem = Wishlist(
                name = formState.title.trim(),
                categoryId = formState.categoryId!!,
                desireRating = formState.desireRating,
                minTargetPrice = formState.minTargetPrice.toDoubleOrNull() ?: 0.0,
                maxTargetPrice = formState.maxTargetPrice.toDoubleOrNull(),
                variationsNote = formState.variationsNote.ifBlank { null },
                prePurchaseNote = formState.prePurchaseNote.ifBlank { null }
            )

            repository.addWishlist(newItem)
            onSuccess()
        }
    }
}