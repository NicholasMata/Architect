package net.matadesigns.architect.core

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun Architect(
    tabs: List<ArchitectTab>,
    startIndex: Int = 0,
    screenGraph: ArchitectScreenGraph,
    navController: NavHostController = rememberNavController()
) {
    val initialTab = tabs[startIndex]
    val currentTab: MutableState<ArchitectTab> = remember { mutableStateOf(initialTab) }
    val currentScreen: MutableState<ArchitectScreen> =
        remember { mutableStateOf(initialTab.start) }
    Scaffold(
        topBar = {
            ArchitectTopAppBar(currentScreen.value.title, navController)
        },
        bottomBar = {
            ArchitectBottomBar(currentTab, tabs, navController)
        }
    )
    {
        ArchitectContent(
            currentTab.value,
            navController
        ) {
            screenGraph.build(this, navController, currentScreen)
        }
    }
}