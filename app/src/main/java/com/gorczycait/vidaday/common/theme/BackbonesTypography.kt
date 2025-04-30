package com.gorczycait.vidaday.common.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextDecoration.Companion.Underline
import androidx.compose.ui.unit.sp
import com.airbnb.android.showkase.annotation.ShowkaseTypography
import com.gorczycait.vidaday.R

private val bigShoulders = FontFamily(
    Font(resId = R.font.bigshoulders_bold, weight = Bold),
    Font(resId = R.font.bigshoulders_extrabold, weight = ExtraBold),
)

private val sora = FontFamily(
    Font(resId = R.font.sora_regular, weight = Normal),
    Font(resId = R.font.sora_medium, weight = Medium),
    Font(resId = R.font.sora_semibold, weight = SemiBold),
    Font(resId = R.font.sora_bold, weight = Bold),
)

private val inter = FontFamily(
    Font(resId = R.font.inter_regular, weight = Normal),
    Font(resId = R.font.inter_medium, weight = Medium),
    Font(resId = R.font.inter_semibold, weight = SemiBold),
    Font(resId = R.font.inter_bold, weight = Bold),
)

private val janeAusten = FontFamily(
    Font(resId = R.font.jane_austen, weight = Normal),
)

private val komoda = FontFamily(
    Font(resId = R.font.komoda, weight = Normal),
)

private val efnRebook = FontFamily(
    Font(resId = R.font.efn_rebook, weight = Normal),
)

object BackbonesTypography {

    // todo: watch https://issuetracker.google.com/issues/227070850 for uppercase style

    @ShowkaseTypography(group = "Display Uppercase")
    val DISPLAY_XXL_BOLD: TextStyle = TextStyle(
        fontFamily = bigShoulders,
        fontWeight = Bold,
        fontSize = 96.sp,
        lineHeight = 104.sp,
        letterSpacing = (-1).sp,
    )

    @ShowkaseTypography(group = "Display Uppercase")
    val DISPLAY_XL_EXTRABOLD: TextStyle = TextStyle(
        fontFamily = bigShoulders,
        fontWeight = ExtraBold,
        fontSize = 40.sp,
        lineHeight = 48.sp,
        letterSpacing = (-1).sp,
    )

    @ShowkaseTypography(group = "Display Uppercase")
    val DISPLAY_L_EXTRABOLD: TextStyle = TextStyle(
        fontFamily = bigShoulders,
        fontWeight = ExtraBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.2).sp,
    )

    @ShowkaseTypography(group = "Display Uppercase")
    val DISPLAY_M_BOLD: TextStyle = TextStyle(
        fontFamily = bigShoulders,
        fontWeight = Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
    )

    @ShowkaseTypography(group = "Display Uppercase")
    val DISPLAY_S_BOLD: TextStyle = TextStyle(
        fontFamily = bigShoulders,
        fontWeight = Bold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
    )

    @ShowkaseTypography(group = "Header Uppercase")
    val HEADER_XXL_BOLD: TextStyle = TextStyle(
        fontFamily = sora,
        fontWeight = Bold,
        fontSize = 32.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    )

    @ShowkaseTypography(group = "Header Uppercase")
    val HEADER_XL_BOLD: TextStyle = TextStyle(
        fontFamily = sora,
        fontWeight = Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
    )

    @ShowkaseTypography(group = "Header Uppercase")
    val HEADER_L_SEMIBOLD: TextStyle = TextStyle(
        fontFamily = sora,
        fontWeight = SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
    )

    @ShowkaseTypography(group = "Header Uppercase")
    val HEADER_M_SEMIBOLD: TextStyle = TextStyle(
        fontFamily = sora,
        fontWeight = SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
    )

    @ShowkaseTypography(group = "Header Uppercase")
    val HEADER_S_BOLD: TextStyle = TextStyle(
        fontFamily = sora,
        fontWeight = Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (0.1).sp,
    )

    @ShowkaseTypography(group = "Header Uppercase")
    val HEADER_XS_BOLD: TextStyle = TextStyle(
        fontFamily = sora,
        fontWeight = Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = (0.2).sp,
    )

    @ShowkaseTypography(group = "Header Uppercase")
    val HEADER_XS_MEDIUM: TextStyle = TextStyle(
        fontFamily = sora,
        fontWeight = Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = (0.1).sp,
    )

    @ShowkaseTypography(group = "Header")
    val Header_M_SemiBold: TextStyle = TextStyle(
        fontFamily = sora,
        fontWeight = SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
    )

    @ShowkaseTypography(group = "Header")
    val Header_S_Bold: TextStyle = TextStyle(
        fontFamily = sora,
        fontWeight = Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
    )

    @ShowkaseTypography(group = "Header")
    val Header_S_Medium: TextStyle = TextStyle(
        fontFamily = sora,
        fontWeight = Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
    )

    @ShowkaseTypography(group = "Header")
    val Header_XS_Bold: TextStyle = TextStyle(
        fontFamily = sora,
        fontWeight = Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
    )

    @ShowkaseTypography(group = "Header")
    val Header_XS_Medium: TextStyle = TextStyle(
        fontFamily = sora,
        fontWeight = Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
    )

    @ShowkaseTypography(group = "Body")
    val Body_L_Medium: TextStyle = TextStyle(
        fontFamily = inter,
        fontWeight = Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.2).sp,
    )

    @ShowkaseTypography(group = "Body")
    val Body_M_Medium: TextStyle = TextStyle(
        fontFamily = inter,
        fontWeight = Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.2).sp,
    )

    @ShowkaseTypography(group = "Body")
    val Body_S_Medium: TextStyle = TextStyle(
        fontFamily = inter,
        fontWeight = Medium,
        fontSize = 13.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.2).sp,
    )

    @ShowkaseTypography(group = "Body")
    val Body_XS_Medium: TextStyle = TextStyle(
        fontFamily = inter,
        fontWeight = Medium,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp,
    )

    @ShowkaseTypography(group = "Body")
    val Body_XS_Medium_Link: TextStyle = TextStyle(
        fontFamily = inter,
        fontWeight = Medium,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp,
        textDecoration = Underline,
    )

    @ShowkaseTypography(group = "Body")
    val Body_XXS_Medium: TextStyle = TextStyle(
        fontFamily = inter,
        fontWeight = Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = (-0.2).sp,
    )

    @ShowkaseTypography(group = "Action")
    val Link_M: TextStyle = TextStyle(
        fontFamily = sora,
        fontWeight = SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.2).sp,
        textDecoration = Underline,
    )

    @ShowkaseTypography(group = "Action")
    val Link_S: TextStyle = TextStyle(
        fontFamily = sora,
        fontWeight = SemiBold,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp,
        textDecoration = Underline,
    )

    @ShowkaseTypography(group = "Action")
    val STICKER: TextStyle = TextStyle(
        fontFamily = sora,
        fontWeight = Bold,
        fontSize = 10.sp,
        lineHeight = 15.sp,
        letterSpacing = (0.2).sp,
    )

    @ShowkaseTypography(group = "Action")
    val Badge: TextStyle = TextStyle(
        fontFamily = sora,
        fontWeight = Bold,
        fontSize = 8.sp,
        lineHeight = 10.sp,
        letterSpacing = 0.sp,
    )

    @ShowkaseTypography(group = "Action")
    val Omnibus: TextStyle = TextStyle(
        fontFamily = sora,
        fontWeight = Normal,
        fontSize = 9.sp,
        lineHeight = 10.sp,
        letterSpacing = 0.sp,
    )

    @ShowkaseTypography(group = "Action")
    val TabBar: TextStyle = TextStyle(
        fontFamily = inter,
        fontWeight = Medium,
        fontSize = 10.sp,
        lineHeight = 12.sp,
        letterSpacing = (-0.2).sp,
    )

    @ShowkaseTypography(group = "Patch")
    val Patch_JaneAusten_S: TextStyle = TextStyle(
        fontFamily = janeAusten,
        fontWeight = Normal,
        fontSize = 16.sp,
        letterSpacing = 0.sp,
    )

    @ShowkaseTypography(group = "Patch")
    val Patch_JaneAusten_L: TextStyle = TextStyle(
        fontFamily = janeAusten,
        fontWeight = Normal,
        fontSize = 32.sp,
        letterSpacing = 0.sp,
    )

    @ShowkaseTypography(group = "Patch")
    val Patch_Komoda_S: TextStyle = TextStyle(
        fontFamily = komoda,
        fontWeight = Normal,
        fontSize = 24.sp,
        letterSpacing = 0.sp,
    )

    @ShowkaseTypography(group = "Patch")
    val Patch_Komoda_L: TextStyle = TextStyle(
        fontFamily = komoda,
        fontWeight = Normal,
        fontSize = 64.sp,
        letterSpacing = 0.sp,
    )

    @ShowkaseTypography(group = "Patch")
    val Patch_EFNRebook_S: TextStyle = TextStyle(
        fontFamily = efnRebook,
        fontWeight = Normal,
        fontSize = 16.sp,
        letterSpacing = 0.sp,
    )

    @ShowkaseTypography(group = "Patch")
    val Patch_EFNRebook_L: TextStyle = TextStyle(
        fontFamily = efnRebook,
        fontWeight = Normal,
        fontSize = 40.sp,
        letterSpacing = 0.sp,
    )
}
