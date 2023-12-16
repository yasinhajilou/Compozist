package com.example.lazylistsample.ui.explorer

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazylistsample.pictureList
import com.example.lazylistsample.theme.AppTheme

const val LIST_ROW_SIZE = 5

@Composable
fun ExplorerScreen() {
    val parentListSize = (pictureList.size / LIST_ROW_SIZE) + (pictureList.size % LIST_ROW_SIZE)
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier =
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface),
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
    ) {
        items(parentListSize) { index ->
            ExplorerItem(index = index)
        }
    }
}

@Preview(name = "light mode")
@Preview(name = "dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExplorerScreenPreview() {
    AppTheme {
        ExplorerScreen()
    }
}
