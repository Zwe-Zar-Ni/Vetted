package com.vaddshah2626.vetted.features.history.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.core.db.toKString
import com.vaddshah2626.vetted.features.photos.data.Photo
import com.vaddshah2626.vetted.core.enums.ItemStatus
import com.vaddshah2626.vetted.features.wishlist.ui.composables.CategoryBadge
import com.vaddshah2626.vetted.features.wishlist.ui.composables.PhotoCarousel
import com.vaddshah2626.vetted.features.history.ui.history.RetireItem
import com.vaddshah2626.vetted.features.wishlist.ui.composables.SourcesField
import com.vaddshah2626.vetted.features.history.ui.history.TimelineHistory
import com.vaddshah2626.vetted.features.history.ui.viewmodels.HistoryDetailsViewModel
import com.vaddshah2626.vetted.features.wishlist.ui.composables.VariationNote
import com.vaddshah2626.vetted.ui.theme.TextTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailsScreen(
    itemId: Int,
    onNavigateBack: () -> Unit
) {

    val scope = rememberCoroutineScope()

    val viewModel: HistoryDetailsViewModel = koinViewModel(
        parameters = { parametersOf(itemId) }
    )
    val history by viewModel.history.collectAsState()

    val item = history?.item
    val category = history?.category
    val photos = history?.photos
    val sources = history?.sources

    var ratingIndicator = ""
    for (i in 0..4) {
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
                    }
                },
                windowInsets = WindowInsets(0, 0, 0, 0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                )
            )
        },
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
                            "Desire Rating : ${item.desireRating} / 5",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextTheme.colors.textSecondary
                        )
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
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Max)
                                .padding(bottom = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
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
                                        style = MaterialTheme.typography.bodyMedium
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
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                            }
                            if (item.actualPricePaid != null) {
                                HorizontalDivider(
                                    modifier = Modifier.width(180.dp)
                                )
                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        "ACTUAL PAID PRICE",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = TextTheme.colors.textSecondary
                                    )
                                    Text(
                                        item.actualPricePaid.toKString(),
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // ? Timeline
            item {
                if (item != null) {
                    Spacer(Modifier.height(20.dp))
                    TimelineHistory(item)
                }
            }

            // ? Variations note
            item {
                if (item != null) {
                    Spacer(Modifier.height(20.dp))
                    VariationNote(
                        note = item.variationsNote,
                        purchasedVariation = item.purchasedVariation,
                        isActionDisabled = true,
                        onEditNote = { _ -> })
                }
            }

            // ? Sources
            item {
                Spacer(Modifier.height(20.dp))
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.surfaceContainer
                )
                Spacer(Modifier.height(20.dp))
                SourcesField(
                    purchasedSourceId = item?.purchasedSourceId,
                    sources = sources ?: emptyList(),
                    isActionsDisabled = true,
                    onAddSource = {},
                    onEditSource = { _, _ -> },
                    onDeleteSource = {}
                )
            }

            item {
                if (item != null && item.status == ItemStatus.PURCHASED) {
                    RetireItem(
                        onRetire = { status, postMortemNote ->
                            scope.launch {
                                viewModel.retireWishlist(
                                    item.copy(
                                        status = status,
                                        postMortemNote = postMortemNote
                                    )
                                )
                            }
                        }
                    )
                }
            }

        }
    }
}