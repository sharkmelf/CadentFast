package com.cadent.cadentfast.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val EditorialSerif = FontFamily.Serif
private val HumanistSans = FontFamily.SansSerif

val CadentTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = EditorialSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 96.sp,
        letterSpacing = (-1.5).sp,
        lineHeight = 100.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = EditorialSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 64.sp,
        letterSpacing = (-0.5).sp,
        lineHeight = 68.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = EditorialSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        letterSpacing = (-0.25).sp,
        lineHeight = 38.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = HumanistSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp,
        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = HumanistSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.5.sp,
        lineHeight = 20.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = HumanistSans,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        letterSpacing = 1.5.sp,
    ),
)
