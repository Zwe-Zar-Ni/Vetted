package com.vaddshah2626.vetted.features.wishlist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.features.wishlist.data.toFormattedDate
import com.vaddshah2626.vetted.features.wishlist.ui.viewmodels.WishlistViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WishlistScreen(viewModel: WishlistViewModel = koinViewModel(), onCreateClick: () -> Unit) {
    val wishlists by viewModel.wishlists.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Wishlist : ${wishlists.size}", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        LazyColumn() {
            items(
                items = wishlists,
                key = { it.item.id }
            ) { itemWithDetails ->
                Column() {
                    Text(
                        text = itemWithDetails.item.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = itemWithDetails.item.status.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = itemWithDetails.category.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = itemWithDetails.item.desireRating.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = itemWithDetails.item.minTargetPrice.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = itemWithDetails.item.maxTargetPrice.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = itemWithDetails.item.variationsNote ?: "",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = itemWithDetails.item.prePurchaseNote ?: "",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = itemWithDetails.item.createdAt.toFormattedDate(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = itemWithDetails.item.coolOffUntil.toFormattedDate(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = onCreateClick
//            onClick = {
//                viewModel.addWishlist(
//                    Wishlist(
//                        name = "Earphones",
//                        categoryId = 1,
//                        desireRating = 1,
//                        minTargetPrice = 100.0,
//                        maxTargetPrice = 150.0,
//                        variationsNote = "variations Note",
//                        prePurchaseNote = "Pre purchase note",
//                    )
//                )
//            }
        ) {
            Text("Add Wishlist")
        }
    }
}