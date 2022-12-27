package com.bitta.app.payment

import android.content.Context
import com.bitta.app.utils.jsonOf
import com.bitta.app.utils.sync
import com.bitta.app.utils.syncOrResolve
import com.bitta.app.utils.toStringAsFixed
import com.google.android.gms.wallet.PaymentDataRequest
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet

class Payment private constructor(private val client: PaymentsClient) {
    companion object {
        fun fromContext(context: Context): Payment {
            val walletOptions =
                Wallet.WalletOptions.Builder().setEnvironment(PaymentConstant.PAYMENTS_ENVIRONMENT)
                    .build()
            return Payment(client = Wallet.getPaymentsClient(context, walletOptions))
        }
    }

    suspend fun isGooglePayAvailable(): Boolean =
        client.isReadyToPay(PaymentConstant.readyToPayRequest).sync()

    suspend fun pay(price: Double, resolveLauncher: GooglePayLauncher): Boolean {
        val requestParams = getPaymentParams(price)
        return client.loadPaymentData(requestParams).syncOrResolve(resolveLauncher) != null
    }

    private fun getPaymentParams(price: Double) = PaymentDataRequest.fromJson(
        jsonOf(
            "apiVersion" to PaymentConstant.API_VERSION,
            "apiVersionMinor" to 0,
            "allowedPaymentMethods" to jsonOf(PaymentConstant.cardPaymentMethod),
            "transactionInfo" to jsonOf(
                "totalPrice" to price.toStringAsFixed(2).replace(',', '.'),
                "totalPriceStatus" to "FINAL",
                "countryCode" to PaymentConstant.COUNTRY_CODE,
                "currencyCode" to PaymentConstant.CURRENCY_CODE,
            ),
            "merchantInfo" to PaymentConstant.merchantInfo,
            "shippingAddressParameters" to jsonOf(
                "phoneNumberRequired" to false,
                "allowedCountryCodes" to jsonOf(PaymentConstant.COUNTRY_CODE)
            ),
            "shippingAddressRequired" to false,
        ).toString()
    )
}
