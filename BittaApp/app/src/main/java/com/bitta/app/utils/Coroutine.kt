package com.bitta.app.utils

import androidx.activity.result.IntentSenderRequest
import com.bitta.app.payment.GooglePayLauncher
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun <TResult> Task<TResult>.syncOrResolve(resolveLauncher: GooglePayLauncher): TResult? {
    return try {
        sync()
    } catch (err: ResolvableApiException) {
        resolveLauncher.launch(IntentSenderRequest.Builder(err.resolution).build())
        null
    }
}

suspend fun <TResult> Task<TResult>.sync(): TResult = suspendCoroutine { continuation ->
    this.addOnCompleteListener { completedTask ->
        if (completedTask.isSuccessful) {
            continuation.resume(completedTask.result)
        } else {
            continuation.resumeWithException(completedTask.exception!!)
        }
    }
}