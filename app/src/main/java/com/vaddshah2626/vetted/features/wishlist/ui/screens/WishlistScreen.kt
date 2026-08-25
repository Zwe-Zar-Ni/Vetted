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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.features.wishlist.ui.composables.WishlistCard
import com.vaddshah2626.vetted.features.wishlist.ui.viewmodels.WishlistViewModel
import com.vaddshah2626.vetted.ui.theme.TextTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun WishlistScreen(
    viewModel: WishlistViewModel = koinViewModel(),
    onCreateClick: () -> Unit,
    onWishlistClick: (wishlistId: Int) -> Unit
) {
    val wishlists by viewModel.wishlists.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkWishlistsStatus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Wishlists",
                style = MaterialTheme.typography.headlineSmall,
                color = TextTheme.colors.textPrimary
            )
            Button(
                onClick = onCreateClick,
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.size(48.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        LazyColumn() {
            items(
                items = wishlists,
                key = { it.item.id }
            ) { itemWithDetails ->
                WishlistCard(itemWithDetails, onWishlistClick = { wishlistId ->
                    onWishlistClick(wishlistId)
                })
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}