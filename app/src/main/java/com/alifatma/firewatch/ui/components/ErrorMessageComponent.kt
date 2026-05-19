package com.alifatma.firewatch.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alifatma.firewatch.R

// A simple error message component, with Lotte animation and text
@Composable
fun ErrorMessageComponent(message: String, modifier: Modifier) {
    Column(modifier = Modifier, horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))
        LottieAnimation(composition, iterations = LottieConstants.IterateForever)
        Text(
            text = message,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Preview
@Composable
fun ErrorMessageComponentPreview() {
    ErrorMessageComponent(message = "An error occurred while fetching data.", modifier = Modifier)
}