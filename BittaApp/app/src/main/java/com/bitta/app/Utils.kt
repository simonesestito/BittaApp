package com.bitta.app

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

const val DELAY_FAKE_LOADING_TIME = 1000L // ms

fun Color.mix(other: Color, ratio: Float) = Color(
    red = this.red * ratio + other.red * (1 - ratio),
    green = this.green * ratio + other.green * (1 - ratio),
    blue = this.blue * ratio + other.blue * (1 - ratio),
    alpha = 1f,
)

fun Number.toStringAsFixed(digits: Int) = "%.${digits}f".format(this)

inline fun <T> LiveData<List<T>>.filter(crossinline filter: (T) -> Boolean): LiveData<List<T>> {
    return MediatorLiveData<List<T>>().apply {
        addSource(this@filter) {
            this.value = it.orEmpty().filter(filter)
        }
    }
}

@Composable
fun TextUnit.toDp() = with(LocalDensity.current) {
    this@toDp.toDp()
}

data class SnackbarInfo(
    @StringRes val message: Int,
    @StringRes val actionLabel: Int? = null,
    val onAction: (() -> Unit)? = null,
)