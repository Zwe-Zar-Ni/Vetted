package com.vaddshah2626.vetted.features.wishlist.ui.composables.wishlist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.features.categories.data.Category
import com.vaddshah2626.vetted.features.wishlist.ui.composables.CategoryBadge
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelector(
    categories: List<Category>,
    value: Int,
    onChange: (id: Int) -> Unit,
    sheetOpen: Boolean,
    onSheetOpenChange: (open: Boolean) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    if (sheetOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                onSheetOpenChange(false)
            },
            sheetState = sheetState,
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    maxItemsInEachRow = 4,
                ) {
                    if (categories.isNotEmpty() ?: false) {
                        for (category in categories) {
                            CategoryBadge(
                                category,
                                modifier = Modifier
                                    .border(
                                        width = 1.dp,
                                        color = if (value == category.id) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.background,
                                        shape = RoundedCornerShape(24.dp)
                                    )
                                    .clip(RoundedCornerShape(24.dp))
                                    .background(
                                        color = if (value == category.id) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.background,
                                        shape = RoundedCornerShape(24.dp)
                                    )
                                    .padding(vertical = 8.dp, horizontal = 12.dp)
                                    .clickable(
                                        onClick = {
                                            onChange(category.id)
                                        }
                                    ),
                                iconTint = if (value == category.id) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onBackground,
                                textColor = if (value == category.id) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onBackground,
                            )
                        }
                    }
                }
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onSheetOpenChange(false)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Done")
                }
            }
        }
    }
}