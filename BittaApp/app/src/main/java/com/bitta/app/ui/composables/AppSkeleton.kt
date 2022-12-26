package com.bitta.app.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.bitta.app.R


@Composable
fun AppSkeleton(
    title: String,
    subtitle: String? = null,
    onBackRoute: (() -> Unit)? = null,
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    AppSkeleton(
        title = title,
        subtitle = subtitle,
        onBackRoute = onBackRoute,
        floatingActionButton = floatingActionButton,
        content = { padding, _ -> content(padding) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSkeleton(
    title: String,
    subtitle: String? = null,
    onBackRoute: (() -> Unit)? = null,
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues, SnackbarHostState) -> Unit,
) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(appBarState)
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
        floatingActionButton = floatingActionButton,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { content(it, snackbarHostState) },
    )
}