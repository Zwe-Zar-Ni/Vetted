package com.vaddshah2626.vetted.features.categories.data

import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryDao: CategoryDao) {
    val categories: Flow<List<Category>> = categoryDao.getAllCategories()

    suspend fun seedInitialCategories(categories: List<Category>) {
        categoryDao.insertAll(categories)
    }
}