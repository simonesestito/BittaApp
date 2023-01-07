package com.bitta.app.payment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitta.app.viewmodel.ProductsViewModel
import com.google.android.gms.wallet.AutoResolveHelper

class GooglePayLauncherContract : ActivityResultContract<IntentSenderRequest, PaymentResult>() {
    override fun createIntent(context: Context, input: IntentSenderRequest): Intent {
        return Intent(ActivityResultContracts.StartIntentSenderForResult.ACTION_INTENT_SENDER_REQUEST).putExtra(
            ActivityResultContracts.StartIntentSenderForResult.EXTRA_INTENT_SENDER_REQUEST, input
        )
    }

    override fun parseResult(resultCode: Int, intent: Intent?): PaymentResult = when (resultCode) {
        Activity.RESULT_CANCELED -> PaymentResult.Canceled
        Activity.RESULT_OK -> PaymentResult.Success
        else -> {
            Log.e(
                "Google Pay Error", "Received status from Google Pay UI flow: ${
                    AutoResolveHelper.getStatusFromIntent(intent)?.statusCode
                }"
            )
            PaymentResult.Error
        }
    }
}

sealed class PaymentResult {
    object Success : PaymentResult()
    object Canceled : PaymentResult()
    object Error : PaymentResult()
}

typealias GooglePayLauncher = ManagedActivityResultLauncher<IntentSenderRequest, PaymentResult>

@Composable
fun rememberGooglePayLauncher(productsViewModel: ProductsViewModel = viewModel()): GooglePayLauncher {
    val context = LocalContext.current
    return rememberLauncherForActivityResult(GooglePayLauncherContract()) { result ->
        productsViewModel.onPurchaseCompleted(context, result)
    }
}