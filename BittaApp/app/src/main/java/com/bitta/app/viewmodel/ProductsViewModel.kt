package com.bitta.app.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.bitta.app.R
import com.bitta.app.datasource.DataSource
import com.bitta.app.datasource.productsForDispenser
import com.bitta.app.datasource.simpleProducts
import com.bitta.app.model.Product
import com.bitta.app.model.ReportedProduct
import com.bitta.app.payment.GooglePayLauncher
import com.bitta.app.payment.Payment
import com.bitta.app.payment.PaymentResult
import com.bitta.app.utils.DELAY_FAKE_LOADING_TIME
import com.bitta.app.utils.getPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {
    private val _loading = MutableLiveData(true)
    private val _query = MutableLiveData("")
    private val _products = MediatorLiveData<List<ReportedProduct>>()
    private var _oldProductsSource: LiveData<List<ReportedProduct>>? = null
    private var dispenserId: Int? = null

    val loading: LiveData<Boolean> = _loading
    val query: LiveData<String> = _query
    val products: LiveData<List<ReportedProduct>> = _products
    var onPurchaseCompleted: ((error: String?) -> Unit)? = null

    init {
        viewModelScope.launch {
            delay(DELAY_FAKE_LOADING_TIME)
            _loading.value = false
        }
    }

    fun search(dispenserId: Int, query: String) {
        this.dispenserId = dispenserId
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

    fun buyProduct(product: Product, context: Context, resolveLauncher: GooglePayLauncher) {
        this.onPurchaseCompleted ?: return

        // Show Google Pay if available
        viewModelScope.launch {
            val payment = Payment.fromContext(context)
            if (!payment.isGooglePayAvailable()) {
                return@launch onPurchaseCompleted?.invoke(
                    context.getString(R.string.google_pay_not_available_error)
                ) ?: Unit
            }

            try {
                val boughtSuccessfully = payment.pay(product.price, resolveLauncher)
                if (boughtSuccessfully) onPurchaseCompleted(context, PaymentResult.Success)
                // else, wait for launched activity to return
            } catch (exception: java.lang.Exception) {
                // Unexpected error
                Log.e("Google Pay Error", "Unexpected payment error", exception)
                onPurchaseCompleted(context, PaymentResult.Error)
            }
        }
    }

    fun onPurchaseCompleted(context: Context, paymentResult: PaymentResult) {
        when (paymentResult) {
            PaymentResult.Success -> {
                // On success, save purchase as not completed
                context.getPreferences().ongoingPurchaseDispenserId = dispenserId!!
                onPurchaseCompleted?.invoke(null)
            }
            PaymentResult.Canceled -> {
                onPurchaseCompleted?.invoke(
                    context.getString(R.string.google_pay_payment_canceled)
                )
            }
            PaymentResult.Error -> {
                onPurchaseCompleted?.invoke(
                    context.getString(R.string.google_pay_unexpected_error)
                )
            }
        }
    }
}
