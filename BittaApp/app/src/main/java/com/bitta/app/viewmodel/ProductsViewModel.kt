package com.bitta.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitta.app.DELAY_FAKE_LOADING_TIME
import com.bitta.app.DataSource
import com.bitta.app.model.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    init {
        viewModelScope.launch {
            delay(DELAY_FAKE_LOADING_TIME)
            _products.postValue(DataSource.products)
        }
    }
}