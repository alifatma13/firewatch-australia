package com.alifatma.firewatch.ui.util

import androidx.compose.ui.graphics.Color
import com.alifatma.firewatch.ui.model.AlertLevel
import com.alifatma.firewatch.ui.model.IncidentStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.DateTimeParseException
import java.util.Locale

internal fun formatPublishedDate(pubDate: String?, fallback: String): String {
    if (pubDate == null) return fallback
    return try {
        val inputFormatter = DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("dd/MM/yyyy h:mm:ss a")
            .toFormatter(Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")
        val dateTime = LocalDateTime.parse(pubDate, inputFormatter)
        dateTime.format(outputFormatter)
    } catch (_: DateTimeParseException) {
        pubDate
    }
}

internal fun String.toCamelCase(): String =
    split(" ", "_", "-").joinToString(" ") { word ->
        word.lowercase(Locale.getDefault())
            .replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
    }

internal fun getColorForStatus(status: String?): Color {
    return when (status?.lowercase(Locale.getDefault())) {
        "under control" -> IncidentStatus.UNDER_CONTROL.color
        "being controlled" -> IncidentStatus.BEING_CONTROLLED.color
        "not yet controlled" -> IncidentStatus.NOT_YET_CONTROLLED.color
        "responding" -> IncidentStatus.RESPONDING.color
        else -> Color.Gray
    }
}

internal fun getColorForAlertLevel(alert: String?): Color{
    return when(alert?.lowercase((Locale.getDefault()))){
        "emergency warning" -> AlertLevel.EMERGENCY_WARNING.color
        "watch and act" -> AlertLevel.WATCH_AND_ACT.color
        "advice" -> AlertLevel.ADVICE.color
        "planned burn" -> AlertLevel.PLANNED_BURN.color
        else-> Color.Gray
    }
}

