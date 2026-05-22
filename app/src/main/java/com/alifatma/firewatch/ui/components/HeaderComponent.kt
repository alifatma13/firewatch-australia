package com.alifatma.firewatch.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifatma.firewatch.ui.theme.FireWatchTypography
import com.alifatma.firewatch.ui.util.TestTags


// Header component for main screen
@Composable
fun HeaderComponent(modifier: Modifier) {
    Column(modifier = modifier.padding(start = 8.dp).testTag(TestTags.HEADER_COMPONENT)) {
        Text(
            text = "TACTICAL OVERVIEW",
            style = FireWatchTypography.titleSmall,
            color = MaterialTheme.colorScheme.tertiary,
            letterSpacing = 2.sp
        )
        Text(
            text = "Active",
            style = FireWatchTypography.displayMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Incidents",
            style = FireWatchTypography.displayMedium,
            color = MaterialTheme.colorScheme.primary,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Last updated: 10 minutes ago",
            style = FireWatchTypography.labelMedium,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Preview
@Composable
fun HeaderComponentPreview() {
    HeaderComponent(modifier = Modifier)
}