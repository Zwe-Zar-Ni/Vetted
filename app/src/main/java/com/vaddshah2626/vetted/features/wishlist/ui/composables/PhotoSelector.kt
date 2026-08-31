package com.vaddshah2626.vetted.features.wishlist.ui.composables

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.vaddshah2626.vetted.ui.theme.TextTheme
import java.io.File

@Composable
fun PhotoSector(
    photoPaths: List<String>,
    onPhotoSelected: (Uri) -> Unit,
    onRemovePhoto: (String) -> Unit
) {
    val context = LocalContext.current

    // Register Photo Picker Launcher
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()
    ) { uris ->
        println(uris)
        if (uris.isNotEmpty()) {
            for (uri in uris) {
                onPhotoSelected(uri)
            }
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            "Photos",
            style = MaterialTheme.typography.bodyLarge,
            color = TextTheme.colors.textTertiary
        )

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Add Photo Button
            item {
                OutlinedCard(
                    modifier = Modifier
                        .size(80.dp)
                        .clickable {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = "Add Photo"
                        )
                    }
                }
            }

            // Thumbnail Gallery
            items(photoPaths) { path ->
                Box(modifier = Modifier.size(80.dp)) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(File(path))
                            .crossfade(true)
                            .build(),
                        contentDescription = "Selected Photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp))
                    )

                    // Remove photo button overlay
                    IconButton(
                        onClick = { onRemovePhoto(path) },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(20.dp)
                            .background(MaterialTheme.colorScheme.error, CircleShape).padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove",
                            tint = MaterialTheme.colorScheme.onError,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}