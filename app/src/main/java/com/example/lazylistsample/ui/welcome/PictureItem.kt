package com.example.lazylistsample.ui.welcome

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun LazyListItem(@DrawableRes drawableId: Int) {
    Image(
        painter = painterResource(id = drawableId),
        contentScale = ContentScale.FillBounds,
        contentDescription = "List Item",
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16))
    )
}