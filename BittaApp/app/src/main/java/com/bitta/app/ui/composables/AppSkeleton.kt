package com.bitta.app.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.bitta.app.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSkeleton(
    title: String,
    subtitle: String? = null,
    onBackRoute: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(appBarState)

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text(title)
                        if (!subtitle.isNullOrBlank()) {
                            Text(subtitle, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    if (onBackRoute != null) {
                        IconButton(onClick = onBackRoute) {
                            Icon(
                                AppIcons.ArrowBack,
                                contentDescription = stringResource(R.string.back_nav_icon),
                            )
                        }
                    }
                })
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        content = content,
    )
}