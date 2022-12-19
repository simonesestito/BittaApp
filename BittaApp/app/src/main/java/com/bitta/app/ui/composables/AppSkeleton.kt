package com.bitta.app.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.bitta.app.ui.theme.BittaAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSkeleton(title: String, content: @Composable (PaddingValues) -> Unit) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(appBarState)

    BittaAppTheme {
        Scaffold(
            topBar = { LargeTopAppBar(title = { Text(text = title) }, scrollBehavior = scrollBehavior) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            content = content,
        )
    }
}