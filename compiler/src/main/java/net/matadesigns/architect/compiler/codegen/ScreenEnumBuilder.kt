package net.matadesigns.architect.compiler.codegen

import com.squareup.kotlinpoet.*
import net.matadesigns.architect.compiler.ClassNames
import net.matadesigns.architect.compiler.GeneratedClassNames
import net.matadesigns.architect.compiler.models.RouteData

class ScreenEnumBuilder(
    private val routes: List<RouteData>
) {
    companion object {
//        internal val className = ClassName("net.matadesigns.architect.navigation", "Screen")
//        internal val architectInterfaceName =
//            ClassName("net.matadesigns.architect", "ArchitectScreen")
    }

    fun build(): TypeSpec {
        val builder = TypeSpec.enumBuilder(GeneratedClassNames.SCREEN)
            .addSuperinterface(ClassNames.ARCHITECT_SCREEN)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter("route", String::class)
                    .addParameter("title", String::class)
                    .build()
            )
            .addProperty(
                PropertySpec.builder("route", String::class, KModifier.PUBLIC)
                    .initializer("route")
                    .addModifiers(KModifier.OVERRIDE)
                    .build()
            )
            .addProperty(
                PropertySpec.builder("title", String::class, KModifier.PUBLIC)
                    .initializer("title")
                    .addModifiers(KModifier.OVERRIDE)
                    .build()
            )
        routes.forEach {
            builder.addEnumConstant(
                it.elementName.removeSuffix("Screen"),
                TypeSpec.anonymousClassBuilder()
                    .addSuperclassConstructorParameter("%S", it.route)
                    .addSuperclassConstructorParameter("%S", it.title)
                    .build()
            )
        }

        return builder.build()
    }
}