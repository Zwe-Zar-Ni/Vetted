package com.vaddshah2626.vetted.features.wishlist.ui.viewmodels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaddshah2626.vetted.features.photos.data.Photo
import com.vaddshah2626.vetted.features.photos.data.PhotoRepository
import com.vaddshah2626.vetted.core.enums.PhotoType
import com.vaddshah2626.vetted.features.sources.data.Source
import com.vaddshah2626.vetted.features.sources.data.SourceRepository
import com.vaddshah2626.vetted.core.enums.ItemStatus
import com.vaddshah2626.vetted.features.wishlist.data.Wishlist
import com.vaddshah2626.vetted.features.wishlist.data.WishlistRepository
import com.vaddshah2626.vetted.features.wishlist.utils.saveImageToInternalStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class WishlistFormState(
    val title: String = "",
    val categoryId: Int? = null,
    val desireRating: Int = 5,
    val minTargetPrice: String = "",
    val maxTargetPrice: String = "",
    val variationsNote: String = "",
    val prePurchaseNote: String = "",
    val selectedPhotoPaths: List<String> = emptyList(),
    val sources: List<Source> = emptyList(),

    val isSubmitting: Boolean = false,
    val titleError: String? = null,
    val categoryError: String? = null
)

class WishlistCreateViewModel(
    private val repository: WishlistRepository,
    private val photoRepository: PhotoRepository,
    private val sourceRepository: SourceRepository
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

    fun onPhotoSelected(context: Context, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val savedPath = saveImageToInternalStorage(context, uri)
            if (savedPath != null) {
                withContext(Dispatchers.Main) {
                    formState = formState.copy(
                        selectedPhotoPaths = formState.selectedPhotoPaths + savedPath
                    )
                }
            }
        }
    }

    fun onRemovePhoto(path: String) {
        formState = formState.copy(
            selectedPhotoPaths = formState.selectedPhotoPaths - path
        )
    }

    fun onSourceAdd(source: Source) {
        formState = formState.copy(
            sources = formState.sources + source
        )
    }

    fun onSourceEdit(index: Int, source: Source) {
        formState = formState.copy(
            sources = formState.sources.mapIndexed { i, src ->
                if (i == index) source else src
            }
        )
    }

    fun onSourceRemove(index: Int) {
        formState = formState.copy(
            sources = formState.sources.filterIndexed { i, _ -> i != index }
        )
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
                status = ItemStatus.WISHLISTED,
                categoryId = formState.categoryId!!,
                desireRating = formState.desireRating,
                minTargetPrice = formState.minTargetPrice.toDoubleOrNull() ?: 0.0,
                maxTargetPrice = formState.maxTargetPrice.toDoubleOrNull(),
                variationsNote = formState.variationsNote.ifBlank { null },
                prePurchaseNote = formState.prePurchaseNote.ifBlank { null }
            )

            val itemId = repository.addWishlist(newItem)

            formState.selectedPhotoPaths.forEach { localFilePath ->
                photoRepository.insertPhoto(
                    Photo(
                        itemId = itemId.toInt(),
                        fileUri = localFilePath,
                        photoType = PhotoType.PRODUCT
                    )
                )
            }

            formState.sources.forEach { source ->
                sourceRepository.insertSource(
                    source.copy(
                        itemId = itemId.toInt()
                    )
                )
            }

            onSuccess()
        }
    }
}