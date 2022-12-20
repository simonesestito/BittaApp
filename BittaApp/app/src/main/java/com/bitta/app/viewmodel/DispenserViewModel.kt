package com.bitta.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitta.app.DELAY_FAKE_LOADING_TIME
import com.bitta.app.datasource.DataSource
import com.bitta.app.datasource.dispensers
import com.bitta.app.model.Dispenser
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DispenserViewModel : ViewModel() {
    private val _dispensers = MutableLiveData<List<Dispenser>>(emptyList())
    val dispensers = _dispensers as LiveData<List<Dispenser>>

    init {
        viewModelScope.launch {
            delay(DELAY_FAKE_LOADING_TIME * 2)
            _dispensers.postValue(DataSource.dispensers)
        }
    }
}