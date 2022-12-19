package com.bitta.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitta.app.DataSource
import com.bitta.app.model.Dispenser
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DispenserViewModel : ViewModel() {
    private val _dispensers = MutableLiveData<List<Dispenser>>(emptyList())
    val dispensers = _dispensers as LiveData<List<Dispenser>>

    init {
        viewModelScope.launch {
            delay(2_000)
            _dispensers.postValue(DataSource.dispensers)
        }
    }
}