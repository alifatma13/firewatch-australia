package com.alifatma.firewatch.ui.model

import androidx.compose.ui.graphics.Color
import com.alifatma.firewatch.ui.theme.AlertAdvice
import com.alifatma.firewatch.ui.theme.BeingControlled
import com.alifatma.firewatch.ui.theme.EmergencyWarning
import com.alifatma.firewatch.ui.theme.NotYetControlled
import com.alifatma.firewatch.ui.theme.PlannedBurn
import com.alifatma.firewatch.ui.theme.Responded
import com.alifatma.firewatch.ui.theme.UnderControl
import com.alifatma.firewatch.ui.theme.WatchAndAct

// Enum for Incident Status with color and description
enum class IncidentStatus(val displayName: String, val description: String, val color: Color) {
    RESPONDING(
        "Responding",
        "An incident has been reported in this location, firefighters are currently responding.",
        Responded
    ),
    NOT_YET_CONTROLLED(
        "Not Yet Controlled",
        "A fire which is spreading on one or more fronts. Effective containment strategies are not in place for the entire perimeter.",
        NotYetControlled
    ),
    BEING_CONTROLLED(
        "Being Controlled",
        "Effective strategies are in operation or planned for the entire perimeter.",
        BeingControlled
    ),
    UNDER_CONTROL(
        "Under Control",
        "The fire is at a stage where fire fighting resources are only required for patrol purposes and major re-ignition is unlikely.",
        UnderControl
    )
}

// Enum for Alert Level with color and description
enum class AlertLevel(val displayName: String, val description: String, val color: Color) {
    EMERGENCY_WARNING(
        "Emergency Warning",
        "An Emergency Warning is the highest level of Bush Fire Alert. You may be in danger and need to take action immediately. Any delay now puts your life at risk.",
        EmergencyWarning
    ),
    WATCH_AND_ACT(
        "Watch and Act",
        "There is a heightened level of threat. Conditions are changing and you need to start taking action now to protect you and your family.",
        WatchAndAct
    ),
    ADVICE(
        "Advice",
        "A fire has started. There is no immediate danger. Stay up to date in case the situation changes.",
        AlertAdvice
    ),
    PLANNED_BURN(
        "Planned Burn",
        "A fire was started as planned",
        PlannedBurn
    )
}
