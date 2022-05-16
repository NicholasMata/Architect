package net.matadesigns.architect.compiler

import com.squareup.kotlinpoet.ClassName
import net.matadesigns.architect.compiler.Constants.ANDROID_NAVIGATION_PACKAGE
import net.matadesigns.architect.compiler.Constants.ANNOTATIONS_PACKAGE
import net.matadesigns.architect.compiler.Constants.ARCHITECT_SCREEN_CLASS
import net.matadesigns.architect.compiler.Constants.ARCHITECT_SCREEN_GRAPH_CLASS
import net.matadesigns.architect.compiler.Constants.CORE_PACKAGE
import net.matadesigns.architect.compiler.Constants.ROUTE_CLASS
import net.matadesigns.architect.compiler.Constants.SCREEN_CLASS
import net.matadesigns.architect.compiler.Constants.SCREEN_GRAPH_CLASS

object ClassNames {
    val ROUTE = ClassName(ANNOTATIONS_PACKAGE, ROUTE_CLASS)
    val ARCHITECT_SCREEN = ClassName(CORE_PACKAGE, ARCHITECT_SCREEN_CLASS)
    val ARCHITECT_SCREEN_GRAPH = ClassName(CORE_PACKAGE, ARCHITECT_SCREEN_GRAPH_CLASS)

    val NAV_GRAPH_BUILDER = ClassName(ANDROID_NAVIGATION_PACKAGE, "NavGraphBuilder")
    val NAV_CONTROLLER = ClassName(ANDROID_NAVIGATION_PACKAGE, "NavController")
    val NAV_BACK_STACK_ENTRY = ClassName(ANDROID_NAVIGATION_PACKAGE, "NavBackStackEntry")
}

object GeneratedClassNames  {
    val SCREEN = ClassName(CORE_PACKAGE, SCREEN_CLASS)
    val SCREEN_GRAPH = ClassName(CORE_PACKAGE, SCREEN_GRAPH_CLASS)
}