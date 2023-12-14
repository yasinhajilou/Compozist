package com.example.lazylistsample.ui.explorer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazylistsample.pictureList

//2*2 Grid list, size of 4
const val GRID_LIST_CELL_SIZE = 4

@Composable
fun ExplorerItem(index: Int) {
    Row(
        modifier = Modifier
            .height(240.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val indexRemainder = index % 2

        val firstImageIndex = index + (index * GRID_LIST_CELL_SIZE)
        var targetImageIndex = firstImageIndex

        //in even items, we compose the large picture first
        if (indexRemainder == 0) {
            CheckSizeThenCompose(currentIndex = targetImageIndex) {
                LargeImage(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(8)),
                    painter = painterResource(id = pictureList[targetImageIndex].id)
                )
                targetImageIndex++
            }
        }

        GridImages(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
            targetImageIndex = targetImageIndex
        )

        //in odd items, we compose the large picture last
        if (indexRemainder == 1) {
            targetImageIndex += GRID_LIST_CELL_SIZE
            CheckSizeThenCompose(currentIndex = targetImageIndex) {
                LargeImage(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(8)),
                    painter = painterResource(id = pictureList[targetImageIndex].id)
                )
            }
        }
    }
}

@Composable
fun LargeImage(modifier: Modifier, painter: Painter) {
    Image(
        painter = painter,
        contentDescription = "Large Image",
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun GridImages(modifier: Modifier, targetImageIndex: Int) {
    var targetIndex = targetImageIndex
    //making a 2*2 Grid List
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(2) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                repeat(2) {
                    CheckSizeThenCompose(currentIndex = targetIndex) {
                        Image(
                            painter = painterResource(id = pictureList[targetIndex].id),
                            contentDescription = "Independent Item",
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    targetIndex++
                }
            }
        }
    }
}

@Composable
fun CheckSizeThenCompose(
    currentIndex: Int,
    composable: @Composable () -> Unit
) {
    if (currentIndex <= pictureList.size - 1) {
        composable()
    }
}

@Preview
@Composable
private fun ExplorerParentItemPreview() {
    ExplorerItem(0)
}