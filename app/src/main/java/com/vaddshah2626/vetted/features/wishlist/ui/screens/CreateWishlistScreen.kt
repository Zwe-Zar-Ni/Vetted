package com.vaddshah2626.vetted.features.wishlist.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.features.wishlist.ui.composables.CategorySelector
import com.vaddshah2626.vetted.features.wishlist.ui.composables.PhotoSector
import com.vaddshah2626.vetted.features.wishlist.ui.composables.SourcesField
import com.vaddshah2626.vetted.features.wishlist.ui.viewmodels.WishlistCreateViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistCreateScreen(
    viewModel: WishlistCreateViewModel = koinViewModel(),
    onNavigateBack: () -> Unit
) {

    val context = LocalContext.current

    val categories by viewModel.categories.collectAsState()
    val state = viewModel.formState

    var categorySheetOpen by remember { mutableStateOf(false) }

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
                        Text(
                            "Add Something",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.align(Alignment.Center)
                        )
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
                onClick = { viewModel.saveWishlistItem(onSuccess = onNavigateBack) },
                enabled = !state.isSubmitting,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    "Save Wishlist Item",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {


            // Item Title
            item {
                OutlinedTextField(
                    value = state.title,
                    onValueChange = viewModel::onTitleChanged,
                    label = { Text("Item Title *") },
                    isError = state.titleError != null,
                    supportingText = state.titleError?.let { { Text(it) } },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                )
            }

            // Category
            item {
                OutlinedButton(
                    onClick = {
                        categorySheetOpen = true
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraSmall,
                    border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            if (state.categoryId != null)
                                categories?.find { it.id == state.categoryId }?.name
                                    ?: ""
                            else "Category",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "DropDown"
                        )
                    }
                }
            }

            // Desire Rating (1-10 Slider or Segmented Buttons)
            item {
                Text("Desire Rating: ${state.desireRating} / 10")
                Slider(
                    value = state.desireRating.toFloat(),
                    onValueChange = { viewModel.onDesireRatingChanged(it.roundToInt()) },
                    valueRange = 1f..10f,
                    steps = 8
                )
            }

            // Target Price Range (Min & Max side-by-side)
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = state.minTargetPrice,
                        onValueChange = viewModel::onMinPriceChanged,
                        label = { Text("Min Price") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = state.maxTargetPrice,
                        onValueChange = viewModel::onMaxPriceChanged,
                        label = { Text("Max Price") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Item Variations note
            item {
                OutlinedTextField(
                    value = state.variationsNote,
                    onValueChange = viewModel::onVariationsNotesChanged,
                    label = { Text("Item Variations") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Words
                    ),
                )
            }

            // Item Pre Purchase note
            item {
                OutlinedTextField(
                    value = state.prePurchaseNote,
                    onValueChange = viewModel::onPrePurchaseNoteChanged,
                    label = { Text("Pre Purchase Note") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                )
            }

            item {
                PhotoSector(
                    photoPaths = state.selectedPhotoPaths,
                    onPhotoSelected = { uri ->
                        viewModel.onPhotoSelected(context, uri)
                    },
                    onRemovePhoto = { path ->
                        viewModel.onRemovePhoto(path)
                    }
                )
            }

            item {
                SourcesField(
                    sources = state.sources,
                    onAddSource = { source -> viewModel.onSourceAdd(source) },
                    onEditSource = { index, source ->
                        viewModel.onSourceEdit(index, source)
                    },
                    onDeleteSource = { index ->
                        viewModel.onSourceRemove(index)
                    }
                )
            }

        }

        CategorySelector(
            categories = categories ?: emptyList(),
            value = state.categoryId ?: 0,
            onChange = { id ->
                viewModel.onCategorySelected(id)
            },
            sheetOpen = categorySheetOpen,
            onSheetOpenChange = { open ->
                categorySheetOpen = open
            }
        )
    }
}
