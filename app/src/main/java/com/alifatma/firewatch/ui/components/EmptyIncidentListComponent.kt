package com.alifatma.firewatch.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alifatma.firewatch.R
import com.alifatma.firewatch.ui.theme.FireWatchTypography
import com.alifatma.firewatch.ui.util.TestTags

@Composable
fun EmptyIncidentListComponent(message: String, modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.testTag(TestTags.EMPTY_STATE),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty_incidents))
            LottieAnimation(
                composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.height(180.dp)
            )
            Text(
                text = message,
                modifier = Modifier
                    .padding(16.dp)
                    .testTag(TestTags.EMPTY_STATE_TEXT),
                style = FireWatchTypography.bodyLarge,
                color = MaterialTheme.colorScheme.outlineVariant,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
@Preview
fun EmptyIncidentListComponentPreview() {
    EmptyIncidentListComponent("No Fires Incidents in NSW", Modifier)
}