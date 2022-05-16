package net.matadesigns.architect.compiler.models

import com.google.devtools.ksp.symbol.KSValueParameter

data class RouteData(
    val packageName: String,
    val elementName: String,
    val elementParams:  List<KSValueParameter>,
    val title: String,
    val route: String
)