package com.vaddshah2626.vetted.features.categories.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaddshah2626.vetted.features.categories.data.CategoryRepository
import com.vaddshah2626.vetted.features.wishlist.data.initialCategories
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: CategoryRepository) : ViewModel() {

    val categories = repository.categories.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun seedInitialCategories() {
        viewModelScope.launch {
            repository.seedInitialCategories(initialCategories)
        }
    }

}