package com.bitta.app.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit

@Composable
fun TextUnit.toDp() = with(LocalDensity.current) {
    this@toDp.toDp()
}