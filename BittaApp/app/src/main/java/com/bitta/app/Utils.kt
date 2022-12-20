package com.bitta.app

import androidx.compose.ui.graphics.Color

const val DELAY_FAKE_LOADING_TIME = 1000L // ms

fun Color.mix(other: Color, ratio: Float) = Color(
    red = this.red * ratio + other.red * (1 - ratio),
    green = this.green * ratio + other.green * (1 - ratio),
    blue = this.blue * ratio + other.blue * (1 - ratio),
    alpha = 1f,
)

fun Number.toStringAsFixed(digits: Int) = "%.${digits}f".format(this)