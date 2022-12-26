package com.bitta.app.ui.composables

import android.Manifest
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.bitta.app.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    when (val cameraStatus = cameraPermissionState.status) {
        PermissionStatus.Granted -> CameraPreviewContent(modifier, cameraSelector, scaleType)
        is PermissionStatus.Denied -> {
            if (!cameraStatus.shouldShowRationale) {
                // Ask for camera permission straight away
                LaunchedEffect(cameraPermissionState) {
                    cameraPermissionState.launchPermissionRequest()
                }
            }

            // Show information
            Column(
                modifier.fillMaxHeight(), Arrangement.Center, Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.camera_permission_description),
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(dimensionResource(R.dimen.app_large_spacing)))
                Button(cameraPermissionState::launchPermissionRequest) {
                    AppButtonContent(AppIcons.Camera, R.string.grant_permission_button)
                }
            }
        }
    }
}

@Composable
private fun CameraPreviewContent(
    modifier: Modifier,
    cameraSelector: CameraSelector,
    scaleType: PreviewView.ScaleType,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(modifier = modifier, factory = { context ->
        val previewView = PreviewView(context).apply {
            this.scaleType = scaleType
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            // Preview is incorrectly scaled in Compose on some devices without this
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
        }

        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            try {
                // Must unbind the use-cases before rebinding them.
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, preview
                )
            } catch (exc: Exception) {
                Log.e("CameraPreview", "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(context))

        previewView
    })
}