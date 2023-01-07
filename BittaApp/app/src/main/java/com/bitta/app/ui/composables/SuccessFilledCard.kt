package com.bitta.app.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import com.bitta.app.R
import com.bitta.app.ui.theme.Success
import com.bitta.app.utils.mix

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessFilledCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    icon: ImageVector = AppIcons.Check,
) {
    ElevatedCard(
        modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.app_large_spacing))
    ) {
        ListItem(
            headlineText = { Text(title) },
            supportingText = { Text(description) },
            leadingContent = { Icon(icon, contentDescription = title) },
            colors = ListItemDefaults.colors(
                containerColor = Color.Success.mix(MaterialTheme.colorScheme.surface),
                leadingIconColor = Color.Success,
            ),
        )
    }
}