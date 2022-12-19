package com.bitta.app.ui.composables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.bitta.app.R

/**
 * Composable to be used inside of a button (Button, OutlineButton, ...)
 * in order to have a good layout with an icon and a label
 */
@Composable
fun RowScope.AppButtonContent(
    icon: Painter,
    label: String,
) {
    Icon(icon, contentDescription = label)
    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.app_small_spacing)))
    Text(label)
}

@Composable
fun RowScope.AppButtonContent(
    icon: ImageVector,
    label: String,
) = AppButtonContent(icon = rememberVectorPainter(icon), label = label)

@Composable
fun RowScope.AppButtonContent(
    icon: ImageVector,
    @StringRes label: Int,
) = AppButtonContent(icon = rememberVectorPainter(icon), label = stringResource(label))

@Composable
fun RowScope.AppButtonContent(
    @DrawableRes icon: Int,
    @StringRes label: Int,
) = AppButtonContent(icon = painterResource(icon), label = stringResource(label))