package com.alifatma.firewatch.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifatma.firewatch.ui.theme.FireWatchTypography
import com.alifatma.firewatch.ui.theme.Primary
import com.alifatma.firewatch.ui.theme.Tertiary


// Header component for main screen
@Composable
fun HeaderComponent(modifier: Modifier) {
    Column(modifier = modifier.padding(start = 8.dp)) {
        Text(
            text = "TACTICAL OVERVIEW",
            style = FireWatchTypography.titleSmall,
            color = Tertiary,
            letterSpacing = 2.sp
        )
        Text(
            text = "Active",
            style = FireWatchTypography.displayMedium,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Incidents",
            style = FireWatchTypography.displayMedium,
            color = Primary,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold

        )

        Text(text = "Last updated: 10 minutes ago", style = FireWatchTypography.labelMedium, color = Tertiary)
    }
}

@Preview
@Composable
fun HeaderComponentPreview() {
    HeaderComponent(modifier = Modifier)
}