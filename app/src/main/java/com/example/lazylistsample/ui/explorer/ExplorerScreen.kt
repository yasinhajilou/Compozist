package com.example.lazylistsample.ui.explorer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazylistsample.pictureList

const val LIST_ROW_SIZE = 5

@Composable
fun ExplorerScreen() {
    val parentListSize = (pictureList.size / LIST_ROW_SIZE) + (pictureList.size % LIST_ROW_SIZE)
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
    ) {
        items(parentListSize) { index ->
            ExplorerItem(index = index)
        }
    }
}

@Preview
@Composable
fun ExplorerScreenPreview() {
    ExplorerScreen()
}