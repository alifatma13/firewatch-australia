package com.alifatma.firewatch.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alifatma.firewatch.R

// Loading Indicator usinf Lotte
@Composable
fun FireLoadingIndicator(modifier: Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fire_loading))
    LottieAnimation(composition, iterations = LottieConstants.IterateForever)
}

 @Preview
 @Composable
    fun FireLoadingIndicatorPreview() {
        FireLoadingIndicator(modifier = Modifier)
    }