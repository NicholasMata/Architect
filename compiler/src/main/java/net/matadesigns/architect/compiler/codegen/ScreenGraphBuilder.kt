package net.matadesigns.architect.compiler.codegen

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import net.matadesigns.architect.compiler.ClassNames
import net.matadesigns.architect.compiler.GeneratedClassNames
import net.matadesigns.architect.compiler.models.RouteData

class ScreenGraphBuilder(
    private val routes: List<RouteData>
) {
    companion object {
//        internal val className = ClassName("net.matadesigns.architect.navigation", "ScreenGraph")
//        internal val interfaceName =
//            ClassName("net.matadesigns.architect", "ArchitectScreenGraph")

        internal val mutableStateName = ClassName(
            "androidx.compose.runtime",
            "MutableState"
        ).parameterizedBy(ClassNames.ARCHITECT_SCREEN)
        // MutableState::class.asClassName()

//        internal val navGraphBuilderClassName = ClassName("androidx.navigation", "NavGraphBuilder")
//        internal val navControllerClassName = ClassName("androidx.navigation", "NavController")
//        internal val navBackStackEntryClassName = ClassName("androidx.navigation", "NavBackStackEntry")
    }

    fun build(): TypeSpec {

        val buildFunBuilder = FunSpec.builder("build")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("builder", ClassNames.NAV_GRAPH_BUILDER)
            .addParameter("navController", ClassNames.NAV_CONTROLLER)
            .addParameter(
                "currentScreen", mutableStateName
            )

        buildFunBuilder.addStatement("builder.apply {")
        routes.forEach {
            val params = it.elementParams.mapNotNull { param ->
                if (param.type.toString() == ClassNames.NAV_BACK_STACK_ENTRY.simpleName) {
                    return@mapNotNull ParameterSpec("it", ClassNames.NAV_BACK_STACK_ENTRY)
                }
                buildFunBuilder.parameters.firstOrNull { it.type.toString() == param.toString() }
            }.map { it.name }.joinToString(",")

            buildFunBuilder.addStatement("""composable(Screen.${it.elementName.removeSuffix("Screen")}, currentScreen) {
                |${it.elementName}($params)
                |}
            """.trimMargin());
        }
        buildFunBuilder.addStatement("}")

        val builder = TypeSpec.classBuilder(GeneratedClassNames.SCREEN_GRAPH)
            .addSuperinterface(ClassNames.ARCHITECT_SCREEN_GRAPH)
            .addFunction(buildFunBuilder.build())
//            .primaryConstructor(
//                FunSpec.constructorBuilder()
//                    .addParameter("route", String::class)
//                    .addParameter("title", String::class)
//                    .build()
//            )
//            .addProperty(
//                PropertySpec.builder("route", String::class, KModifier.PUBLIC)
//                    .initializer("route")
//                    .addModifiers(KModifier.OVERRIDE)
//                    .build()
//            )
//            .addProperty(
//                PropertySpec.builder("title", String::class, KModifier.PUBLIC)
//                    .initializer("title")
//                    .addModifiers(KModifier.OVERRIDE)
//                    .build()
//            )
//        routes.forEach {
//            builder.addEnumConstant(
//                it.elementName.removeSuffix("Screen"),
//                TypeSpec.anonymousClassBuilder()
//                    .addSuperclassConstructorParameter("%S", it.route)
//                    .addSuperclassConstructorParameter("%S", it.title)
//                    .build()
//            )
//        }
//
        return builder.build()
    }
}