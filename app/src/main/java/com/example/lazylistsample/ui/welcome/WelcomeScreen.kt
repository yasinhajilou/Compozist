package com.example.lazylistsample.ui.welcome

import android.content.res.Configuration
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lazylistsample.R
import com.example.lazylistsample.pictureList
import com.example.lazylistsample.theme.AppTheme
import kotlinx.coroutines.isActive

private val scrollingSpeeds = arrayOf(45f, 40f, 30f)
private const val LIST_ITEM_COUNT = 6

@Composable
fun WelcomeScreen(onNextButtonClicked: () -> Unit) {
    Surface(
        modifier =
        Modifier
            .fillMaxSize(),
    ) {
        LoopLists()
        BottomDetails(
            modifier = Modifier,
            onNextButtonClicked = onNextButtonClicked,
        )
    }
}

@Composable
fun LoopLists() {
    Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.tertiaryContainer)) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier =
            Modifier
                .fillMaxWidth()
                .weight(6f),
        ) {
            repeat(3) {
                // reverse direction of the middle list
                CircularList(
                    circularListIndex = it,
                    reverse = it % 2 == 0,
                    modifier = Modifier.weight(1f),
                )
            }
        }
        Spacer(modifier = Modifier.weight(2f))
    }
}

@Composable
fun BottomDetails(
    modifier: Modifier,
    onNextButtonClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(5f))
        Column(
            modifier =
            Modifier
                .weight(3f)
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CutCornerShape(topStartPercent = 5, topEndPercent = 5),
                )
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.welcome_content),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
            )
            NextButton(onButtonClicked = onNextButtonClicked)
        }
    }
}

@Composable
fun NextButton(onButtonClicked: () -> Unit) {
    var showLoading by rememberSaveable {
        mutableStateOf(false)
    }
    val widthDp by animateDpAsState(
        targetValue = if (showLoading) 82.dp else 140.dp,
        animationSpec = tween(),
        label = "width_anim",
    )
    Button(
        onClick = {
            showLoading = !showLoading
            onButtonClicked()
        },
        modifier = Modifier
            .height(46.dp)
            .width(widthDp),
        enabled = !showLoading,
        colors = ButtonDefaults.buttonColors(disabledContainerColor = MaterialTheme.colorScheme.primary),
    ) {
        if (showLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.align(CenterVertically),
            )
        } else {
            Text(
                text = stringResource(R.string.welcome_btn_content),
                fontSize = 15.sp,
            )
        }
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            showLoading = false
        }
    }
}

@Composable
fun CircularList(
    modifier: Modifier,
    circularListIndex: Int,
    reverse: Boolean,
    listState: LazyListState =
        rememberLazyListState(initialFirstVisibleItemIndex = Int.MAX_VALUE / 2),
) {
    val firstPicIndex = (circularListIndex * LIST_ITEM_COUNT)
    val lastPicIndex = firstPicIndex + LIST_ITEM_COUNT
    val currentPicturesList = pictureList.subList(firstPicIndex, lastPicIndex)

    LazyRow(
        modifier = modifier,
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        userScrollEnabled = false,
    ) {
        items(Int.MAX_VALUE) { index ->
            val itemIndex = index % LIST_ITEM_COUNT
            LazyListItem(drawableId = currentPicturesList[itemIndex].id)
        }
    }
    LaunchedEffect(key1 = Unit) {
        val animationSpec: AnimationSpec<Float> = tween(easing = LinearEasing)
        while (isActive) {
            smoothScrollWithAnimation(
                reverse = reverse,
                index = circularListIndex,
                animationSpec = animationSpec,
                listState = listState,
            )
        }
    }
}

private suspend fun smoothScrollWithAnimation(
    reverse: Boolean,
    index: Int,
    animationSpec: AnimationSpec<Float>,
    listState: LazyListState,
) {
    var previousValue = if (reverse) scrollingSpeeds[index] else 0f
    val initValue = if (reverse) scrollingSpeeds[index] else 0f
    val targetValue = if (reverse) 0f else scrollingSpeeds[index]
    listState.scroll(MutatePriority.PreventUserInput) {
        animate(
            initialValue = initValue,
            targetValue = targetValue,
            animationSpec = animationSpec,
        ) { currentValue, _ ->
            previousValue += scrollBy(currentValue - previousValue)
        }
    }
}

@Preview(name = "light mode")
@Preview(name = "dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WelcomeScreenPreview() {
    AppTheme {
        WelcomeScreen({})
    }
}
