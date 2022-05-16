package net.matadesigns.architect.compiler

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.*
import net.matadesigns.architect.annotations.Route
import net.matadesigns.architect.compiler.Constants.ROUTE_CLASS
import net.matadesigns.architect.compiler.codegen.ScreenGraphBuilder
import net.matadesigns.architect.compiler.extensions.writeTo
import java.io.OutputStream

class RouteProcessor(
    private val environment: SymbolProcessorEnvironment,
    private val options: Map<String, String>,
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {

    operator fun OutputStream.plusAssign(str: String) {
        this.write(str.toByteArray())
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        logger.warn("STARTED")
        val symbols = resolver
            // Getting all symbols that are annotated with @Route.
            .getSymbolsWithAnnotation(Route::class.qualifiedName!!)
            // Making sure we take only function declarations.
            .filterIsInstance<KSFunctionDeclaration>()


        val unableToProcess = symbols.filterNot { it.validate() }.toList()

        logger.warn("unableToProcess ${unableToProcess.size}")
        // Exit from the processor in case nothing is annotated with @Route.
        if (!symbols.iterator().hasNext()) return unableToProcess

        val screenTypeBuilder = TypeSpec.enumBuilder(GeneratedClassNames.SCREEN)
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

        symbols.forEach { it.accept(ScreenVisitor(screenTypeBuilder), Unit) }

        FileSpec.builder(
            GeneratedClassNames.SCREEN.packageName,
            GeneratedClassNames.SCREEN.simpleName
        )
            .addType(screenTypeBuilder.build())
            .build()
            .writeTo(codeGenerator, Dependencies(aggregating = false))


        val screenGraphBuildFunBuilder = FunSpec.builder("build")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("builder", ClassNames.NAV_GRAPH_BUILDER)
            .addParameter("navController", ClassNames.NAV_CONTROLLER)
            .addParameter(
                "currentScreen", ScreenGraphBuilder.mutableStateName
            )
            .addStatement("builder.apply {")

        val screenGraphFileBuilder = FileSpec.builder(
            GeneratedClassNames.SCREEN_GRAPH.packageName,
            GeneratedClassNames.SCREEN_GRAPH.simpleName
        )

        symbols.forEach {
            it.accept(
                ScreenGraphVisitor(
                    screenGraphBuildFunBuilder,
                    screenGraphFileBuilder,
                    resolver
                ), Unit
            )
        }

        screenGraphBuildFunBuilder.addStatement("}")

        val screenGraphBuilder = TypeSpec.classBuilder(GeneratedClassNames.SCREEN_GRAPH)
            .addSuperinterface(ClassNames.ARCHITECT_SCREEN_GRAPH)
            .addFunction(screenGraphBuildFunBuilder.build())

        screenGraphFileBuilder
            .addType(screenGraphBuilder.build())
            .build()
            .writeTo(codeGenerator, Dependencies(aggregating = false))

        logger.warn("FINISHED")

        return unableToProcess
    }

    inner class ScreenGraphVisitor(
        private val builder: FunSpec.Builder,
        private val fileBuilder: FileSpec.Builder,
        private val resolver: Resolver
    ) : KSVisitorVoid() {
        override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
            val functionName = function.simpleName.asString()

            val routeAnnotation =
                function.annotations.first { it.shortName.asString() == ROUTE_CLASS }
            val route =
                routeAnnotation.arguments.first { it.name?.asString() == "route" }.value as String
            val routeVariables = "\\{(.*?)\\}".toRegex().findAll(route).mapNotNull{it.groups[1]?.value}.toList()
            fileBuilder.addImport(function.packageName.asString(), functionName)
            val cleanedFunctionName = functionName.removeSuffix("Screen")

            val params = function.parameters.mapNotNull { param ->
                val paramName = param.name?.asString()
                val paramType = param.type.resolve()
                val paramTypeQualifiedName = param.type.resolve().declaration.qualifiedName?.asString()
                if (paramTypeQualifiedName == ClassNames.NAV_BACK_STACK_ENTRY.toString()) {
                    return@mapNotNull ParameterSpec("it", ClassNames.NAV_BACK_STACK_ENTRY)
                }
                val parameter = builder.parameters.firstOrNull { it.type.toString() == paramTypeQualifiedName }

                val isRoutedVariable = routeVariables.contains(paramName)

                if(isRoutedVariable && !paramTypeQualifiedName.isNullOrBlank()) {
                    return@mapNotNull ParameterSpec("it.arguments?.get(\"${paramName}\") as ${paramTypeQualifiedName}${if (paramType.isMarkedNullable) "?" else "" }", ClassName.bestGuess(paramTypeQualifiedName))
                }
                parameter
            }.map { it.name }.joinToString(",")

            builder.addStatement(
                """composable(Screen.${cleanedFunctionName}, currentScreen) {
                |${functionName}($params)
                |}
            """.trimMargin()
            );
        }
    }

    inner class ScreenVisitor(private val builder: TypeSpec.Builder) : KSVisitorVoid() {
        override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
            val functionName = function.simpleName.asString()
            val routeAnnotation =
                function.annotations.first { it.shortName.asString() == ROUTE_CLASS }
            val route =
                routeAnnotation.arguments.first { it.name?.asString() == "route" }.value as String
            val title =
                routeAnnotation.arguments.first { it.name?.asString() == "title" }.value as String

            builder.addEnumConstant(
                functionName.removeSuffix("Screen"),
                TypeSpec.anonymousClassBuilder()
                    .addSuperclassConstructorParameter("%S", route)
                    .addSuperclassConstructorParameter("%S", title)
                    .build()
            )
        }
    }
}
