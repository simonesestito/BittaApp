package com.bitta.app.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit

private const val SHARED_PREFS_NAME = "app-prefs"
private const val APP_PREF_ONGOING_PURCHASE_DISPENSER_ID = "ongoingPurchaseDispenserId"
private const val PREFERENCE_INT_NULL_VALUE = -1

class AppPreferences(private val sharedPrefs: SharedPreferences) {
    var ongoingPurchaseDispenserId: Int?
        get() = getIntPreference(APP_PREF_ONGOING_PURCHASE_DISPENSER_ID)
        set(value) = setIntPreference(APP_PREF_ONGOING_PURCHASE_DISPENSER_ID, value)

    private fun getIntPreference(name: String) = sharedPrefs.getInt(
        name, PREFERENCE_INT_NULL_VALUE
    ).let { if (it == PREFERENCE_INT_NULL_VALUE) null else it }

    private fun setIntPreference(name: String, value: Int?) = sharedPrefs.edit {
        putInt(
            name, value ?: PREFERENCE_INT_NULL_VALUE
        )
    }
}

fun Context.getPreferences() = AppPreferences(
    getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE)
)