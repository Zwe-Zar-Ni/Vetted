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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
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
import com.vaddshah2626.vetted.features.wishlist.data.ItemStatus
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RetireItem(
    onRetire: (status: ItemStatus, postMortemNote: String?) -> Unit
) {
    var status by remember { mutableStateOf(ItemStatus.RETIRED) }
    var postMortemNote by remember { mutableStateOf("") }

    var sheetOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    val availableStatuses = arrayOf(
        ItemStatus.RETIRED, ItemStatus.LOST, ItemStatus.USED_UP,
        ItemStatus.DAMAGED, ItemStatus.BROKEN
    )

    Button(
        onClick = {
            sheetOpen = true
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Retire Item",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium
        )
    }

    if (sheetOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                status = ItemStatus.RETIRED
                postMortemNote = ""
                sheetOpen = false
            },
            sheetState = sheetState,
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    maxItemsInEachRow = 3,
                ) {
                    for (sts in availableStatuses) {
                        Button(
                            onClick = {
                                status = sts
                            },
                            colors = ButtonColors(
                                containerColor = if (sts == status) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainer,
                                contentColor = if (sts == status) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                disabledContainerColor = MaterialTheme.colorScheme.background,
                                disabledContentColor = MaterialTheme.colorScheme.onBackground
                            )
                        ) {
                            Text(
                                sts.name.split("_").joinToString(separator = " ").lowercase()
                                    .replaceFirstChar { it.uppercase() })
                        }
                    }
                }
                OutlinedTextField(
                    value = postMortemNote,
                    onValueChange = { value: String ->
                        postMortemNote = value
                    },
                    label = { Text("What Happened? *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                )

                Spacer(Modifier.height(24.dp))
                Button(
                    enabled = status != ItemStatus.PURCHASED,
                    onClick = {
                        onRetire(
                            status,
                            postMortemNote.trim().ifEmpty { null }
                        )
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                sheetOpen = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Retire Item")
                }
            }
        }
    }
}