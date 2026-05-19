package com.alifatma.firewatch.ui.theme

import androidx.compose.ui.graphics.Color

// --- Primary: Urgency Red ---
val Primary = Color(0xFFAD2C00)
val OnPrimary = Color(0xFFFFFFFF)
val PrimaryContainer = Color(0xFFD34011) // gradient endpoint
val OnPrimaryContainer = Color(0xFFFFFFFF)

// --- Secondary: Tactical Blue-Grey ---
val Secondary = Color(0xFF4C616C)
val OnSecondary = Color(0xFFFFFFFF)
val SecondaryContainer = Color(0xFFCFE6F2)
val OnSecondaryContainer = Color(0xFF071E27)

// --- Tertiary: Warning Amber ---
val Tertiary = Color(0xFF765700)
val OnTertiary = Color(0xFFFFFFFF)
val TertiaryContainer = Color(0xFFFFDEA5)
val OnTertiaryContainer = Color(0xFF251A00)

// --- Surface Hierarchy (The No-Line Rule) ---
val SurfaceContainerLowest = Color(0xFFFFFFFF)  // highest lift (card on panel)
val SurfaceContainerLow    = Color(0xFFF3F3F3)  // inactive/background panels
val Surface                = Color(0xFFF9F9F9)  // base layer
val SurfaceContainer       = Color(0xFFFFF8F6)  // primary content containers
val CardContainer       = Color(0xFFFFF8F6)  // primary content containers
val SurfaceContainerHigh   = Color(0xFFE8E8E8)  // active/selected zones
val SurfaceContainerHighest= Color(0xFFE2E2E2)  // input track fill

// --- On Surface ---
val OnSurface        = Color(0xFF1A1C1C) // never pure black
val OnSurfaceVariant = Color(0xFF5A4138) // editorial small-cap labels

// --- Outline & Ghost Border ---
val Outline        = Color(0xFF8F7066)              // bottom-accent on focused input
val OutlineVariant = Color(0xFFE3BFB2)              // ghost border at 15% opacity

// --- Error ---
val Error   = Color(0xFFBA1A1A)
val OnError = Color(0xFFFFFFFF)

// ── Dark Surface Hierarchy ────────────────────────────────────────────────────
val DarkSurfaceContainerLowest = Color(0xFF111414)
val DarkSurfaceContainerLow    = Color(0xFF191C1C)
val DarkSurface                = Color(0xFF1A1C1C)
val DarkSurfaceContainer       = Color(0xFF1E2121)
val DarkCardContainer          = Color(0xFF272120) // warm dark equivalent of CardContainer
val DarkSurfaceContainerHigh   = Color(0xFF282B2B)
val DarkSurfaceContainerHighest= Color(0xFF333636)

// ── Dark On-Surface ───────────────────────────────────────────────────────────
val DarkOnSurface        = Color(0xFFE1E3E3)
val DarkOnSurfaceVariant = Color(0xFFBFAEA8) // warm tint preserved

// ── Dark Outline ──────────────────────────────────────────────────────────────
val DarkOutline        = Color(0xFF9A8078)
val DarkOutlineVariant = Color(0xFF52342A)

//--Incident Severity Colors---
val UnderControl = Color(0xFF388E3C)
val BeingControlled = Color(0xFFB34700)
val NotYetControlled = Color(0xFFE84918)
val Responded = Color(0xFF1976D2)

//--Alert Level Colors---

val EmergencyWarning = Color(0xFFD84315)
val WatchAndAct = Color(0xFFFFA000)
val AlertAdvice = Color(0xFF2F6F73)
val PlannedBurn = Color(0xFF388E3C)


