package net.matadesigns.architect.core

import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface ArchitectScreenGraph {
    fun build(
        builder: NavGraphBuilder,
        navController: NavController,
        currentScreen: MutableState<ArchitectScreen>
    )
}