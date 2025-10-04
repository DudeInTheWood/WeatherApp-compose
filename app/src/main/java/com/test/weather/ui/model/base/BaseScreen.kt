package com.test.weather.ui.model.base

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackBarHost: @Composable () -> Unit = {},
    useStatusBarPadding: Boolean = true,
    useNavigationBarPadding: Boolean = true,
    content: @Composable (PaddingValues) -> Unit
) {
    val inset = when {
        useStatusBarPadding && useNavigationBarPadding -> WindowInsets.systemBars
        useStatusBarPadding -> WindowInsets.statusBars
        useNavigationBarPadding -> WindowInsets.navigationBars
        else -> WindowInsets(0.dp)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = snackBarHost,
        contentWindowInsets = inset
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            content(paddingValues)
        }
    }
}
