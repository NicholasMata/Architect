package net.matadesigns.architect.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.*
import androidx.navigation.NavHostController

@Composable
fun ArchitectContent(
    currentTab: ArchitectTab,
    navController: NavHostController,
    builder: NavGraphBuilder.() -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = currentTab.start.route,
        builder = builder
    )
}