package net.matadesigns.architect.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.*
import androidx.navigation.compose.composable

fun NavGraphBuilder.composable(
    screen: ArchitectScreen,
    currentScreen: MutableState<ArchitectScreen>,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(screen.route) {
        currentScreen.value = screen
        content(it)
    }
}