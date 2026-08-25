package com.vaddshah2626.vetted.features.wishlist.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.core.db.toKString
import com.vaddshah2626.vetted.features.sources.data.Source
import com.vaddshah2626.vetted.shared.components.ConfirmationDialog
import com.vaddshah2626.vetted.ui.theme.TextTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SourcesField(
    sources: List<Source>,
    onAddSource: (source: Source) -> Unit,
    onEditSource: (index: Int, source: Source) -> Unit,
    onDeleteSource: (index: Int) -> Unit
) {

    var title by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    var isEditing by remember { mutableStateOf(false) }
    var editIndex by remember { mutableIntStateOf(0) }
    var sheetOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var dialogOpen by remember { mutableStateOf(false) }
    var sourceIndexToDelete by remember { mutableIntStateOf(0) }

    fun addNewSource() {
        val source = Source(
            itemId = 0,
            title = title,
            url = url.ifEmpty { null },
            price = if (price.isEmpty()) null else price.toDouble()
        )
        onAddSource(source)
        title = ""
        url = ""
        price = ""
    }

    fun editSource() {
        val source = Source(
            itemId = 0,
            title = title,
            url = url.ifEmpty { null },
            price = if (price.isEmpty()) null else price.toDouble()
        )
        onEditSource(editIndex, source)
        title = ""
        url = ""
        price = ""
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Available Sources",
                style = MaterialTheme.typography.bodyLarge,
                color = TextTheme.colors.textTertiary
            )
            IconButton(
                onClick = {
                    sheetOpen = true
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add More Source",
                    tint = TextTheme.colors.textSecondary
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        for ((i, src) in sources.withIndex()) {
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
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(30.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        src.title,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextTheme.colors.textSecondary
                    )
                    if (src.price != null) {
                        Spacer(Modifier.height(2.dp))
                        Text(
                            src.price.toKString(),
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextTheme.colors.textPrimary
                        )
                    }
                }
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = TextTheme.colors.textTertiary,
                    modifier = Modifier
                        .size(21.dp)
                        .clickable(
                            onClick = {
                                title = src.title
                                url = src.url ?: ""
                                price = if (src.price != null) src.price.toString() else ""
                                isEditing = true
                                editIndex = i
                                sheetOpen = true
                            },
                        )
                )
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = TextTheme.colors.textTertiary,
                    modifier = Modifier
                        .size(21.dp)
                        .clickable(
                            onClick = {
                                sourceIndexToDelete = i
                                dialogOpen = true
                            },
                        )
                )
            }
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.surfaceContainer
            )
        }
        if (sheetOpen) {
            ModalBottomSheet(
                onDismissRequest = {
                    title = ""
                    url = ""
                    price = ""
                    sheetOpen = false
                },
                sheetState = sheetState,
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { value: String ->
                            title = value
                        },
                        label = { Text("Source Name *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next,
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                    )
                    OutlinedTextField(
                        value = url,
                        onValueChange = { value ->
                            url = value
                        },
                        label = { Text("Source Url") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Uri,
                            imeAction = ImeAction.Next,
                            capitalization = KeyboardCapitalization.None
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = price,
                        onValueChange = { value ->
                            price = value
                        },
                        label = { Text("Price") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(24.dp))
                    Button(
                        enabled = title.isNotEmpty(),
                        onClick = {
                            if (isEditing) {
                                editSource()
                            } else {
                                addNewSource()
                            }
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    sheetOpen = false
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (isEditing) "Edit" else "Add")
                    }
                }
            }
        }

        if (dialogOpen) {
            ConfirmationDialog(
                title = "Are you sure?",
                description = "Are you sure to delete this source?",
                onDismiss = {
                    sourceIndexToDelete = 0
                    dialogOpen = false
                },
                onConfirm = {
                    onDeleteSource(sourceIndexToDelete)
                }
            )
        }
    }
}