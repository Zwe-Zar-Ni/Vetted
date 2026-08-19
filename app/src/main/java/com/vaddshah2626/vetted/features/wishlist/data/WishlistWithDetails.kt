package com.vaddshah2626.vetted.features.wishlist.data

import androidx.room.Embedded
import androidx.room.Relation
import com.vaddshah2626.vetted.features.categories.data.Category

data class WishlistWithDetails(
    @Embedded
    val item: Wishlist,

    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: Category,

//    @Relation(
//        parentColumn = "id",
//        entityColumn = "item_id"
//    )
//    val sources: List<SourceEntity>,
//
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "item_id"
//    )
//    val photos: List<PhotoEntity>
)