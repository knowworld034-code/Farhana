package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.R

@Composable
fun ProductImage(
    imageUri: String,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    when {
        imageUri.startsWith("img_") -> {
            val drawableId = context.resources.getIdentifier(imageUri, "drawable", context.packageName)
            if (drawableId != 0) {
                Image(
                    painter = painterResource(id = drawableId),
                    contentDescription = contentDescription,
                    modifier = modifier,
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.img_hero_banner),
                    contentDescription = contentDescription,
                    modifier = modifier,
                    contentScale = ContentScale.Crop
                )
            }
        }
        imageUri.isNotBlank() -> {
            AsyncImage(
                model = imageUri,
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.img_hero_banner)
            )
        }
        else -> {
            Image(
                painter = painterResource(id = R.drawable.img_hero_banner),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = ContentScale.Crop
            )
        }
    }
}
