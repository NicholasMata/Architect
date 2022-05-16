package net.matadesigns.architect.core

import android.os.Bundle
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ArchitectBottomBar(
    currentTab: MutableState<ArchitectTab>,
    tabs: List<ArchitectTab>,
    navController: NavController
) {
    BottomNavigation(
        elevation = 5.dp
    ) {
        tabs.forEach {
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = null
                    )
                },
                label = { Text(it.title) },
                selected = currentTab.value == it,
                onClick = {
                    val oldTab = currentTab.value
                    oldTab.navState = navController.saveState() ?: Bundle()
                    navController.restoreState(it.navState)
                    currentTab.value = it
                }
            )
        }
    }
}