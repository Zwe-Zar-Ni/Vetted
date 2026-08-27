package com.vaddshah2626.vetted.features.wishlist.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.core.db.toFormattedDate
import com.vaddshah2626.vetted.core.db.toKString
import com.vaddshah2626.vetted.features.photos.data.Photo
import com.vaddshah2626.vetted.features.wishlist.data.ItemStatus
import com.vaddshah2626.vetted.features.wishlist.ui.composables.wishlist.BuyItemAction
import com.vaddshah2626.vetted.features.wishlist.ui.composables.CategoryBadge
import com.vaddshah2626.vetted.features.wishlist.ui.composables.PhotoCarousel
import com.vaddshah2626.vetted.features.wishlist.ui.composables.SourcesField
import com.vaddshah2626.vetted.features.wishlist.ui.composables.VariationNote
import com.vaddshah2626.vetted.features.wishlist.ui.viewmodels.WishlistDetailsViewModel
import com.vaddshah2626.vetted.ui.theme.TextTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistDetailsScreen(
    wishlistId: Int,
    onNavigateBack: () -> Unit
) {

    val scope = rememberCoroutineScope()

    val viewModel: WishlistDetailsViewModel = koinViewModel(
        parameters = { parametersOf(wishlistId) }
    )
    val wishlist by viewModel.wishlist.collectAsState()

    val item = wishlist?.item
    val category = wishlist?.category
    val photos = wishlist?.photos
    val sources = wishlist?.sources

    var ratingIndicator = ""
    for (i in 0..9) {
        ratingIndicator += if (i < (item?.desireRating ?: 0)) {
            "♥\uFE0E"
        } else {
            "♡"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp, 0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                            contentDescription = "Back",
                            modifier = Modifier.clickable(
                                onClick = onNavigateBack
                            )
                        )
                        if (item != null) {
                            Button(
                                onClick = {
                                    scope.launch {
                                        viewModel.updateWishlist(
                                            item.copy(
                                                status = ItemStatus.READY
                                            )
                                        )
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.background,
                                    contentColor = MaterialTheme.colorScheme.onBackground,
                                )
                            ) {
                                Text("Cancel")
                            }
                        }
                    }
                },
                windowInsets = WindowInsets(0, 0, 0, 0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                )
            )
        },
        bottomBar = {
            if (item?.status == ItemStatus.READY) {
                BuyItemAction(
                    wishlist = item,
                    sources = sources ?: emptyList(),
                    onBuy = { wishlist ->
                        scope.launch {
                            viewModel.purchaseWishlist(wishlist)
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            // ? Photo carousel
            item {
                PhotoCarousel(
                    photos = photos ?: emptyList(),
                    onDeletePhoto = { index ->
                        val photo = photos?.get(index) ?: return@PhotoCarousel
                        scope.launch {
                            viewModel.deletePhoto(photo)
                        }
                    },
                    onAddPhoto = { uri ->
                        scope.launch {
                            viewModel.addPhoto(
                                Photo(
                                    fileUri = uri,
                                    itemId = item?.id ?: 0
                                )
                            )
                        }
                    }
                )
            }

            // ? item name, category, rating
            item {
                if (item != null) {
                    Spacer(Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(item.name, style = MaterialTheme.typography.headlineLarge)
                        if (category != null) CategoryBadge(category, null)
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = ratingIndicator,
                            color = TextTheme.colors.textSecondary
                        )
                        Text(
                            "Desire Rating : ${item.desireRating} / 10",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextTheme.colors.textSecondary
                        )
                    }
                }
            }

            // ? Pre Purchase Note
            item {
                if (!item?.prePurchaseNote.isNullOrEmpty()) {
                    Spacer(Modifier.height(20.dp))
                    Text(item.prePurchaseNote)
                }
            }

            // ? Cool off warning card
            item {
                if (item?.status == ItemStatus.WISHLISTED) {
                    Spacer(Modifier.height(20.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer.copy(0.4f)
                        ),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Warning",
                                modifier = Modifier
                                    .size(28.dp)
                                    .padding(2.dp),
                                tint = MaterialTheme.colorScheme.onErrorContainer
                            )
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    "Ready to buy at",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextTheme.colors.textSecondary
                                )
                                Text(
                                    item.coolOffUntil.toFormattedDate(),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }

            // ? Price Range
            item {
                if (item != null) {
                    Spacer(Modifier.height(20.dp))
                    OutlinedCard(
                        shape = MaterialTheme.shapes.small,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "MIN TARGET PRICE",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextTheme.colors.textTertiary
                                )
                                Text(
                                    item.minTargetPrice.toKString(),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            if (item.maxTargetPrice != null) {
                                VerticalDivider(
                                    modifier = Modifier.height(24.dp)
                                )
                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        "MAX TARGET PRICE",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextTheme.colors.textTertiary
                                    )
                                    Text(
                                        item.maxTargetPrice.toKString(),
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // ? Variations note
            item {
                if (item != null) {
                    Spacer(Modifier.height(20.dp))
                    VariationNote(
                        note = item.variationsNote,
                        onEditNote = { note ->
                        scope.launch {
                            viewModel.updateWishlist(
                                item.copy(
                                    variationsNote = note
                                )
                            )
                        }
                    })
                }
            }

            // ? Sources
            item {
                Spacer(Modifier.height(20.dp))
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.surfaceContainer
                )
                SourcesField(
                    sources = sources ?: emptyList(),
                    onAddSource = { src ->
                        scope.launch {
                            viewModel.addSource(
                                src.copy(
                                    itemId = item?.id ?: 0
                                )
                            )
                        }
                    },
                    onEditSource = { index, src ->
                        val originalSource = sources?.get(index) ?: return@SourcesField
                        scope.launch {
                            viewModel.updateSource(
                                originalSource.copy(
                                    title = src.title,
                                    url = src.url,
                                    price = src.price,
                                )
                            )
                        }
                    },
                    onDeleteSource = { index ->
                        val src = sources?.get(index) ?: return@SourcesField
                        scope.launch {
                            viewModel.deleteSource(src)
                        }
                    }
                )
            }

        }
    }
}