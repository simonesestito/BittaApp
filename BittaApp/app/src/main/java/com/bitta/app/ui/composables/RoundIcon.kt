package com.bitta.app.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import com.bitta.app.R

@Composable
fun RoundIcon(icon: ImageVector, label: String, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .size(dimensionResource(R.dimen.round_icon_container_size))
            .clip(CircleShape)
            .background(color)
    ) {
        Icon(
            modifier = Modifier.align(Alignment.Center),
            imageVector = icon,
            contentDescription = label,
            tint = Color.White,
        )
    }
}