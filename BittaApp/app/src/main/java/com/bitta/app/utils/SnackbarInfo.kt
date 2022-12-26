package com.bitta.app.utils

import androidx.annotation.StringRes

data class SnackbarInfo(
    @StringRes val message: Int,
    @StringRes val actionLabel: Int? = null,
    val onAction: (() -> Unit)? = null,
)