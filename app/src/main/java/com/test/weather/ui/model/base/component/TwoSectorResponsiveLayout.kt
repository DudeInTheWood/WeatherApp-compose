package com.test.weather.ui.model.base.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TwoSectorResponsiveLayout(
    isWideScreen: Boolean,
    modifier: Modifier = Modifier,
    startContent: @Composable (Modifier) -> Unit,
    endContent: @Composable (Modifier) -> Unit,
    startWeight: Float = 1f,
    secondWeight: Float = 2f,
    spacing: Dp = 12.dp
) {
    if (isWideScreen) {
        Row(
            modifier = modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            Column(
                modifier = Modifier
                    .weight(startWeight)
                    .verticalScroll(rememberScrollState())
            ) {
                startContent(Modifier.fillMaxWidth())
            }
            Column(
                modifier = Modifier
                    .weight(secondWeight)
                    .verticalScroll(rememberScrollState())
            ) {
                endContent(Modifier.fillMaxWidth())
            }
        }
    } else {
        Column(
            modifier = modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(spacing)
        ) {
            startContent(Modifier.fillMaxWidth())
            endContent(Modifier.fillMaxWidth())
        }
    }
}