package com.vaddshah2626.vetted.features.wishlist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.vaddshah2626.vetted.core.db.toFormattedDate
import com.vaddshah2626.vetted.core.db.toKString
import com.vaddshah2626.vetted.features.wishlist.data.ItemStatus
import com.vaddshah2626.vetted.features.wishlist.ui.composables.CategoryBadge
import com.vaddshah2626.vetted.features.wishlist.ui.composables.VariationNote
import com.vaddshah2626.vetted.features.wishlist.ui.viewmodels.WishlistDetailsViewModel
import com.vaddshah2626.vetted.ui.theme.TextTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.io.File


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistDetailsScreen(
    wishlistId: Int,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current

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

    val carouselState = rememberCarouselState(itemCount = { photos?.size ?: 0 })

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        IconButton(
                            onClick = onNavigateBack,
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                                contentDescription = "Back",
                            )
                        }
                        IconButton(
                            onClick = onNavigateBack,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                            )
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
            Button(
                onClick = { },
                enabled = item?.status == ItemStatus.READY,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    "Buy Item",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium
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
                if (!photos.isNullOrEmpty()) {
                    HorizontalMultiBrowseCarousel(
                        state = carouselState,
                        preferredItemWidth = 320.dp,
                        itemSpacing = 8.dp,

                        modifier = Modifier
                            .height(280.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) { index ->
                        val photo = photos[index]
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(File(photo.fileUri))
                                .crossfade(true)
                                .build(),
                            contentDescription = "Photo ${photo.id}",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }
                }
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
                    Text(item.prePurchaseNote ?: "")
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
                                        item.minTargetPrice.toKString(),
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
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Variations",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextTheme.colors.textTertiary
                        )
                        Spacer(Modifier.height(4.dp))
                        VariationNote(item.variationsNote, null)
                    }
                }
            }

            // ? Sources
            item {
                if (!sources.isNullOrEmpty()) {
                    Spacer(Modifier.height(20.dp))
                    Text(
                        "Available Sources",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextTheme.colors.textTertiary
                    )
                    Spacer(Modifier.height(4.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        for (src in sources) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Link,
                                    contentDescription = "Source",
                                    tint = TextTheme.colors.textSecondary
                                )
                                Text(
                                    src.title,
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    tint = TextTheme.colors.textTertiary,
                                    modifier = Modifier
                                        .size(21.dp)
                                )
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = TextTheme.colors.textTertiary,
                                    modifier = Modifier
                                        .size(21.dp)
                                )
                            }
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.surfaceContainer
                            )
                        }
                    }
                }
            }

        }
    }
}