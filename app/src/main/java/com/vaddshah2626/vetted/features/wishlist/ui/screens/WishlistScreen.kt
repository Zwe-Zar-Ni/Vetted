package com.vaddshah2626.vetted.features.wishlist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.features.wishlist.ui.composables.WishlistCard
import com.vaddshah2626.vetted.features.wishlist.ui.viewmodels.WishlistViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WishlistScreen(viewModel: WishlistViewModel = koinViewModel(), onCreateClick: () -> Unit) {
    val wishlists by viewModel.wishlists.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp , 0.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Wishlist : ${wishlists.size}", style = MaterialTheme.typography.headlineMedium)
            Button(
                onClick = onCreateClick,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text("Add Item")
            }
        }
        Spacer(Modifier.height(8.dp))
        LazyColumn() {
            items(
                items = wishlists,
                key = { it.item.id }
            ) { itemWithDetails ->
                WishlistCard(itemWithDetails)
            }
        }
    }
}