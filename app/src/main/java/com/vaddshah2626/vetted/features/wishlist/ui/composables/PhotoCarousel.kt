package com.vaddshah2626.vetted.features.wishlist.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.vaddshah2626.vetted.features.photos.data.Photo
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoCarousel(
    photos : List<Photo>
) {
    val context = LocalContext.current
    val carouselState = rememberCarouselState(itemCount = { photos.size ?: 0 })

    HorizontalMultiBrowseCarousel(
        state = carouselState,
        preferredItemWidth = 320.dp,
        itemSpacing = 8.dp,

        modifier = Modifier
            .height(280.dp)
            .clip(RoundedCornerShape(12.dp))
    ) { index ->
        val photo = photos[index]
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
}