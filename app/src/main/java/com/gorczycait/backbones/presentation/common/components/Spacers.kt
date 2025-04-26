package com.gorczycait.backbones.presentation.common.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun HorizontalSpacer(width: Dp) = Spacer(modifier = Modifier.width(width))

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun VerticalSpacer(height: Dp) = Spacer(modifier = Modifier.height(height))

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun RowScope.WeightSpacer(weight: Float = 1f) = Spacer(modifier = Modifier.weight(weight))

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun ColumnScope.WeightSpacer(weight: Float = 1f) = Spacer(modifier = Modifier.weight(weight))
