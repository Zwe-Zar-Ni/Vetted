package com.vaddshah2626.vetted.features.wishlist.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyItemAction(
    onBuy: (actualPricePaid: Double?, purchaseNote: String?) -> Unit
) {

    var actualPricePaid by remember { mutableStateOf("") }
    var purchaseNote by remember { mutableStateOf("") }

    var sheetOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

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
                        onBuy(actualPricePaid.toDoubleOrNull(), purchaseNote)
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