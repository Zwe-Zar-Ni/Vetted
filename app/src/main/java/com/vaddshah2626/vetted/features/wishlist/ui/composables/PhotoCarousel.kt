package com.vaddshah2626.vetted.features.wishlist.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.vaddshah2626.vetted.features.photos.data.Photo
import com.vaddshah2626.vetted.shared.components.ConfirmationDialog
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoCarousel(
    photos: List<Photo>
) {
    val context = LocalContext.current
    val carouselState = rememberCarouselState(itemCount = { photos.size + 1 })

    var dialogOpen by remember { mutableStateOf(false) }
    var photoIndexToDelete by remember { mutableIntStateOf(0) }

    HorizontalMultiBrowseCarousel(
        state = carouselState,
        preferredItemWidth = 320.dp,
        itemSpacing = 8.dp,

        modifier = Modifier
            .height(280.dp)
            .clip(RoundedCornerShape(12.dp))
    ) { index ->
        if (index <= photos.size - 1) {
            val photo = photos[index]
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .zIndex(1f)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = MaterialTheme.shapes.extraSmall
                        )
                        .padding(4.dp).clickable(
                            onClick = {
                                photoIndexToDelete = index
                                dialogOpen = true
                            }
                        )
                )
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
        } else {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        onClick = {},
                        shape = CircleShape,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = "Add Photo"
                        )
                    }
                }
            }
        }
    }

    if(dialogOpen) {
        ConfirmationDialog(
            title = "Are you sure?",
            description = "Are you sure to delete this photo?",
            onDismiss = {
                photoIndexToDelete = 0
                dialogOpen = false
            },
            onConfirm = {

            }
        )
    }
}