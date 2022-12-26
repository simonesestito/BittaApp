package com.bitta.app.utils

fun Number.toStringAsFixed(digits: Int) = "%.${digits}f".format(this)
