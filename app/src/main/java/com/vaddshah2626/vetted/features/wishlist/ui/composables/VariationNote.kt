package com.vaddshah2626.vetted.features.wishlist.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.ui.theme.TextTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VariationNote(
    note: String?,
    onEditNote: (note: String) -> Unit
) {
    val notes = note?.split(",") ?: emptyList()

    var formNote by remember { mutableStateOf(note ?: "") }

    var sheetOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Variations",
            style = MaterialTheme.typography.bodyLarge,
            color = TextTheme.colors.textTertiary
        )
        Spacer(Modifier.height(4.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (n in notes) {
                item {
                    Card(
                        shape = MaterialTheme.shapes.small,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        )
                    ) {
                        Text(
                            n,
                            modifier = Modifier.padding(12.dp, 6.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
            item {
                Spacer(Modifier.width(4.dp))
                IconButton(
                    onClick = {
                        sheetOpen = true
                    },
                    modifier = Modifier.size(21.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = TextTheme.colors.textTertiary
                    )
                }
            }
        }
    }

    if (sheetOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                formNote = ""
                sheetOpen = false
            },
            sheetState = sheetState,
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                OutlinedTextField(
                    value = formNote,
                    onValueChange = { value: String ->
                        formNote = value
                    },
                    label = { Text("Separated by comma ,") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                )
                Spacer(Modifier.height(24.dp))
                Button(
                    enabled = formNote.isNotEmpty(),
                    onClick = {
                        onEditNote(formNote)
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