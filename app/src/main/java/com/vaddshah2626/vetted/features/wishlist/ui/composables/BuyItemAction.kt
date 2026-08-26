package com.vaddshah2626.vetted.features.wishlist.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.features.sources.data.Source
import com.vaddshah2626.vetted.features.wishlist.data.Wishlist
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyItemAction(
    wishlist: Wishlist,
    sources: List<Source>,
    onBuy: (wishlist: Wishlist) -> Unit
) {

    var actualPricePaid by remember { mutableStateOf("") }
    var purchaseNote by remember { mutableStateOf("") }
    var purchasedSourceId by remember { mutableIntStateOf(0) }
    var purchasedVariation by remember { mutableStateOf("") }

    val availableVariations = wishlist.variationsNote?.split(",") ?: emptyList()

    var sheetOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    fun buyItem() {
        onBuy(
            wishlist.copy(
                actualPricePaid = actualPricePaid.toDoubleOrNull(),
                purchaseNote = purchaseNote.ifEmpty { null },
                purchasedSourceId = if (purchasedSourceId != 0) purchasedSourceId else null,
                purchasedVariation = purchasedVariation.ifEmpty { null }
            )
        )
    }

    Button(
        onClick = {
            sheetOpen = true
        },
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

    if (sheetOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                actualPricePaid = ""
                purchaseNote = ""
                sheetOpen = false
            },
            sheetState = sheetState,
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                OutlinedTextField(
                    value = actualPricePaid,
                    onValueChange = { value ->
                        actualPricePaid = value
                    },
                    label = { Text("Actual Price Paid") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(5.dp))
                Text("Purchased Variation")
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    maxItemsInEachRow = 3,
                ) {
                    for (variation in availableVariations) {
                        Button(
                            onClick = {
                                purchasedVariation = variation
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (purchasedVariation == variation) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainer,
                                contentColor = if (purchasedVariation == variation) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                            )
                        ) {
                            Text(variation)
                        }
                    }
                }

                Spacer(Modifier.height(5.dp))
                Text("Purchased Source")
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    maxItemsInEachRow = 3,
                ) {
                    for (src in sources) {
                        Button(
                            onClick = {
                                purchasedSourceId = src.id
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (purchasedSourceId == src.id) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainer,
                                contentColor = if (purchasedSourceId == src.id) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                            )
                        ) {
                            Text(src.title)
                        }
                    }
                }

                Spacer(Modifier.height(5.dp))
                OutlinedTextField(
                    value = purchaseNote,
                    onValueChange = { value ->
                        purchaseNote = value
                    },
                    label = { Text("Purchase Note") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                )
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {
                        buyItem()
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                sheetOpen = false
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