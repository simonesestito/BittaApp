package com.bitta.app.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

inline fun <T> LiveData<List<T>>.filter(crossinline filter: (T) -> Boolean): LiveData<List<T>> {
    return MediatorLiveData<List<T>>().apply {
        addSource(this@filter) {
            this.value = it.orEmpty().filter(filter)
        }
    }
}