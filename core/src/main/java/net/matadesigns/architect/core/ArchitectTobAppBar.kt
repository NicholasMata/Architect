package net.matadesigns.architect.core

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

@Composable
fun ArchitectTopAppBar(title: String, navController: NavController) {
    TopAppBar(
        title = {
            Text(title)
        },
        navigationIcon = NavigationIcon(navController)
    )
}

@Composable
fun NavigationIcon(navController: NavController): @Composable (() -> Unit)? {
    val previousBackStackEntry: NavBackStackEntry? by navController.previousBackStackEntryAsState()
    return previousBackStackEntry?.let {
        {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Up button")
            }
        }
    }
}

@Composable
internal fun NavController.previousBackStackEntryAsState(): State<NavBackStackEntry?> {
    val previousNavBackStackEntry = remember { mutableStateOf(previousBackStackEntry) }
    // setup the onDestinationChangedListener responsible for detecting when the
    // previous back stack entry changes
    DisposableEffect(this) {
        val callback = NavController.OnDestinationChangedListener { controller, _, _ ->
            previousNavBackStackEntry.value = controller.previousBackStackEntry
        }
        addOnDestinationChangedListener(callback)
        // remove the navController on dispose (i.e. when the composable is destroyed)
        onDispose {
            removeOnDestinationChangedListener(callback)
        }
    }
    return previousNavBackStackEntry
}

