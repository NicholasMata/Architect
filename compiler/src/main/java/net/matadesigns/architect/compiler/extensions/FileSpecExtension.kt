package net.matadesigns.architect.compiler.extensions

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.squareup.kotlinpoet.FileSpec

fun FileSpec.writeTo(
    codeGenerator: CodeGenerator,
    dependencies: Dependencies
) {
    codeGenerator.createNewFile(dependencies, this.packageName, this.name).use { outputStream ->
        outputStream.writer().use {
            this.writeTo(it)
        }
        outputStream.close()
    }
}