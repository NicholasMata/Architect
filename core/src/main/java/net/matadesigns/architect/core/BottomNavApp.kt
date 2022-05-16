package net.matadesigns.architect.core

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

//@Composable
//fun TopBar(currentTab: Screen) {
//    TopAppBar(
//        title = {
//            Text(currentTab.route)
//        },
//        navigationIcon = NavigationIcon(navController)
//    )
//}
//
//@Composable
//fun NavigationIcon(navController: NavController): @Composable (() -> Unit)? {
//    val previousBackStackEntry: NavBackStackEntry? by navController.previousBackStackEntryAsState()
//    return previousBackStackEntry?.let {
//        {
//            IconButton(onClick = {
//                navController.popBackStack()
//            }) {
//                Icon(Icons.Default.ArrowBack, contentDescription = "Up button")
//            }
//        }
//    }
//}
//
//interface Screen {
//    val route: String
//    val title: String
//}
//
//interface Tab {
//    val title: String
//    val start: Screen
//    val icon: ImageVector
//}
//
//enum class AppScreen(override val route: String, override val title: String) : Screen {
//    Home("home", "Home"),
//    More("more", "More"),
//    Category("category", "Category"),
//    CategoryDetail("categoryDetail", "Category Detail")
//}
//
//enum class AppTab(
//    override val title: String,
//    override val icon: ImageVector,
//    override val start: Screen
//) : Tab {
//    Home(AppScreen.Home.title, Icons.Default.Home, AppScreen.Home),
//    More(AppScreen.More.title, Icons.Default.Settings, AppScreen.More)
//}
//
////@Composable
////fun BottomNavApp() {
////    var currentTab by rememberSaveable(saver = ScreenSaver()) { mutableStateOf(Screen.Profile) }
////    Surface(color = MaterialTheme.colors.background) {
////        Scaffold(
////            topBar =
////        )
////    }
////}
////
////fun ScreenSaver(): Saver<MutableState<Screen>, *> = Saver(
////    save = { it.value.saveState() },
////    restore = { mutableStateOf(Screen.restoreState(it)) }
////)
////
////fun NavStateSaver(): Saver<MutableState<Bundle>, out Any> = Saver(
////    save = { it.value },
////    restore = { mutableStateOf(it) }
////)
////
////interface Screen {
////    val route: String
////    fun saveState(startDestination: String): Bundle
////}
////
////sealed class AppScreen(override val route: String): Screen {
////    object Home: AppScreen("Home")
////
////    override fun saveState(startDestination: String): Bundle {
////        return bundleOf(SCREEN_KEY to getScreenId(route, startDestination))
////    }
////
////    companion object {
////        const val SCREEN_KEY = "route"
////
////        fun restoreState(bundle: Bundle): Screen {
////            val key = bundle.getString(SCREEN_KEY)
////
////        }
////
////        fun getScreenId(route: String, startDestination: String): String {
////            return "$startDestination+$route"
////        }
////    }
////}
////
//////sealed class Screen(val route: String, val root:String) {
//////    fun saveState(): Bundle {
//////        return bundleOf(KEY_SCREEN to route)
//////    }
//////
//////    fun restoreState(bundle: Bundle): Screen {
//////
//////    }
//////
//////    companion object {
//////        fun restoreState(bundle: Bundle): Screen {
//////            val title = bundle.getString(KEY_SCREEN, Profile.route)
//////            return when (title) {
//////                Profile.route -> Profile
//////                Dashboard.route -> Dashboard
//////                DashboardDetail.route -> DashboardDetail
//////                Phrases.route -> Phrases
//////                PhraseDetail.route -> PhraseDetail
//////                else -> Profile
//////            }
//////        }
//////
//////        const val KEY_SCREEN = "route"
//////
//////        fun getScreenId(route: String, root: String): String {
//////            return "$route+$root"
//////        }
//////    }
////}