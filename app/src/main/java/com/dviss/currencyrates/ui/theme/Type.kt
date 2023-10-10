package com.dviss.currencyrates.ui.theme

import android.os.Build
import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dviss.currencyrates.R

val default = FontFamily(
    Font(
        R.font.inter_medium
    ),
    Font(
        R.font.inter_semibold
    ),
    Font(
        R.font.inter_bold
    )
)

@OptIn(ExperimentalTextApi::class)
val interFontFamily = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    FontFamily(
        Font(
            resId = R.font.inter_source,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(500)
            )
        ),
        Font(
            resId = R.font.inter_source,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(700)
            )
        ),
        Font(
            resId = R.font.inter_source,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(600)
            )
        )
    )
} else {
    default
}

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    )
)