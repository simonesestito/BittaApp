package com.bitta.app.payment

import com.bitta.app.utils.jsonOf
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.WalletConstants

object PaymentConstant {
    const val PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST
    const val API_VERSION = 2
    const val CURRENCY_CODE = "EUR"
    const val COUNTRY_CODE = "IT"

    val merchantInfo = jsonOf(
        "merchantName" to "BittaApp",
    )

    private val gatewayTokenizationSpecification = jsonOf(
        "type" to "PAYMENT_GATEWAY",
        "parameters" to jsonOf(
            "gateway" to "example",
            "gatewayMerchantId" to "exampleGatewayMerchantId",
        ),
    )

    private val allowedCardNetworks = jsonOf(
        "MASTERCARD",
        "VISA",
    )

    private val allowedCardAuthMethods = jsonOf(
        "PAN_ONLY",
        "CRYPTOGRAM_3DS",
    )

    val cardPaymentMethod = jsonOf(
        "type" to "CARD",
        "parameters" to jsonOf(
            "allowedAuthMethods" to allowedCardAuthMethods,
            "allowedCardNetworks" to allowedCardNetworks,
            "billingAddressRequired" to false,
        ),
        "tokenizationSpecification" to gatewayTokenizationSpecification,
    )

    val readyToPayRequest = IsReadyToPayRequest.fromJson(
        jsonOf(
            "apiVersion" to API_VERSION,
            "apiVersionMinor" to 0,
            "allowedPaymentMethods" to jsonOf(cardPaymentMethod),
        ).toString()
    )
}