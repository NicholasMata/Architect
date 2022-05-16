package net.matadesigns.architectandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import net.matadesigns.architect.annotations.Route
import net.matadesigns.architect.core.*
import net.matadesigns.architectandroid.ui.theme.ArchitectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArchitectTheme {
                Architect(
                    tabs = Tab.values().toList(),
                    screenGraph = ScreenGraph()
                )
            }
        }
    }
}

enum class Tab(
    override val id: String,
    override val title: String,
    override val start: ArchitectScreen,
    override val icon: ImageVector,
    override var navState: Bundle = Bundle()
) : ArchitectTab {
    Home("home", "Home", Screen.Home, Icons.Default.Home),
    Settings("settings", "Settings", Screen.Settings, Icons.Default.Settings)
}


@Composable
@Route("settings", "Settings")
fun SettingsScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = {
            navController.navigate(Screen.ThemeSettings.route.replace("{id}", "1234"))
        }) {
            Text(text = "Go To Theme Settings")
        }
    }
}

@Composable
@Route("home", "Home")
fun HomeScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = {
            navController.navigate(Screen.Settings.route)
        }) {
            Text(text = "Go To Settings")
        }
    }
}

@Composable
@Route("settings/theme?id={id}", "Theme Settings")
fun ThemeSettingsScreen(id: String?) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Route Variables: ${getVariablesFromRoute(Screen.ThemeSettings.route)}")
        Text("ID is $id")
    }
}

fun getVariablesFromRoute(route: String): List<String> {
    return "\\{(.*?)\\}".toRegex().findAll(route).mapNotNull{it.groups[1]?.value}.toList()
}