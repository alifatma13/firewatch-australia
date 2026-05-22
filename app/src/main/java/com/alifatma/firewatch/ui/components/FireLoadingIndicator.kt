package com.alifatma.firewatch.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alifatma.firewatch.R
import com.alifatma.firewatch.ui.util.TestTags

// Loading Indicator usinf Lotte
@Composable
fun FireLoadingIndicator(modifier: Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fire_loading))
    Box(
        modifier = modifier
            .testTag(TestTags.LOADING_INDICATOR)
            .semantics { contentDescription = "Loading" }
    ) {
        LottieAnimation(
            composition,
            iterations = LottieConstants.IterateForever,
        )
    }
}

@Preview
@Composable
fun FireLoadingIndicatorPreview() {
    FireLoadingIndicator(modifier = Modifier)
}