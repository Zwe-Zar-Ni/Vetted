package com.vaddshah2626.vetted.features.wishlist.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.R
import com.vaddshah2626.vetted.core.db.toFormattedDate
import com.vaddshah2626.vetted.core.db.toKString
import com.vaddshah2626.vetted.features.categories.data.Category
import com.vaddshah2626.vetted.features.wishlist.ui.composables.CategoryBadge
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
    val categories by viewModel.categories.collectAsState()
    val currentMonthSpending by viewModel.currentMonthSpending.collectAsState()

    var filteredWishlists by remember { mutableStateOf(wishlists) }
    var selectedCategory by remember { mutableIntStateOf(0) }

    fun onCategoryChange(id: Int) {
        selectedCategory = id
        if (id == 0) {
            filteredWishlists = wishlists
            return
        }
        filteredWishlists = wishlists.filter { id == it.category.id }
    }

    LaunchedEffect(Unit) {
        viewModel.checkWishlistsStatus()
    }

    LaunchedEffect(wishlists) {
        filteredWishlists = wishlists
        selectedCategory = 0
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
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Box(Modifier.fillMaxWidth().height(100.dp).padding(12.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.calendar),
                    contentDescription = "Clock Icon",
                    modifier = Modifier.size(100.dp).align(Alignment.TopEnd).graphicsLayer(alpha = 0.7f)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                ) {
                    Text(
                        System.currentTimeMillis().toFormattedDate("MMM YYYY"),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        currentMonthSpending.toKString(),
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                CategoryBadge(
                    Category(0, "All", icon = "other"),
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = if (selectedCategory == 0) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            color = if (selectedCategory == 0) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                        .clickable(
                            onClick = {
                                onCategoryChange(0)
                            }
                        ),
                    iconTint = if (selectedCategory == 0) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onBackground,
                    textColor = if (selectedCategory == 0) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onBackground,
                )
            }
            if (categories?.isNotEmpty() ?: false) {
                for (category in categories) {
                    item {
                        CategoryBadge(
                            category,
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = if (selectedCategory == category.id) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.background,
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .clip(RoundedCornerShape(24.dp))
                                .background(
                                    color = if (selectedCategory == category.id) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.background,
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .padding(vertical = 8.dp, horizontal = 12.dp)
                                .clickable(
                                    onClick = {
                                        onCategoryChange(category.id)
                                    }
                                ),
                            iconTint = if (selectedCategory == category.id) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onBackground,
                            textColor = if (selectedCategory == category.id) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        LazyColumn {
            items(
                items = filteredWishlists,
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