package com.bitta.app.viewmodel

import androidx.lifecycle.*
import com.bitta.app.DELAY_FAKE_LOADING_TIME
import com.bitta.app.datasource.DataSource
import com.bitta.app.datasource.productsForDispenser
import com.bitta.app.datasource.simpleProducts
import com.bitta.app.model.ReportedProduct
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {
    private val _loading = MutableLiveData(true)
    private val _query = MutableLiveData("")
    private val _products = MediatorLiveData<List<ReportedProduct>>()
    private var _oldProductsSource: LiveData<List<ReportedProduct>>? = null

    val loading: LiveData<Boolean> = _loading
    val query: LiveData<String> = _query
    val products: LiveData<List<ReportedProduct>> = _products

    init {
        viewModelScope.launch {
            delay(DELAY_FAKE_LOADING_TIME)
            _loading.value = false
        }
    }

    fun search(dispenserId: Int, query: String) {
        _query.value = query
        if (_oldProductsSource != null) {
            _products.removeSource(_oldProductsSource!!)
        }

        _oldProductsSource = DataSource.productsForDispenser(dispenserId)
        _products.addSource(_oldProductsSource!!) { allProducts ->
            if (query.isBlank()) {
                // No query
                _products.value = allProducts
            } else {
                // Search products
                _products.value = allProducts.filter {
                    it.product.name.contains(query, ignoreCase = true)
                }
            }
        }
    }

    fun getProductById(id: Int) = DataSource.simpleProducts.find { it.id == id }!!
}
