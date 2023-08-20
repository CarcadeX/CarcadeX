package me.redtea.genrepojsonbukkit.json

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.*
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toClassName
import generateJsonRepo
import me.redtea.exposedgenerator.annotations.ID
import me.redtea.genrepojsonbukkit.annotations.JsonRepo
import java.io.OutputStreamWriter

class ExposedVisitor(private val codeGenerator: CodeGenerator,
                     private val logger: KSPLogger
) : KSVisitorVoid() {
    var repos = mutableListOf<String>()

    override fun visitFile(file: KSFile, data: Unit) {

        val packageName = file.packageName.asString()
        repos.add(
            """
package $packageName
import me.redtea.carcadex.data.kotlinextensions.repo
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val json = Json
""".trimIndent()
        )
        file.declarations.filter { it is KSPropertyDeclaration }.forEach { it.accept(this, Unit) }
        file.declarations.filter { it is KSClassDeclaration }.forEach { it.accept(this, Unit) }
        try {
            codeGenerator.createNewFile(Dependencies(true), file.packageName.asString(), file.fileName+"Repos").use {
                OutputStreamWriter(it).use { writer ->
                    writer.write(repos.joinToString("\n"))
                }
            }
        } catch (ignored: FileAlreadyExistsException) {
        }
    }


    @OptIn(KspExperimental::class)
    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        if(classDeclaration.isAnnotationPresent(JsonRepo::class)) {
            val plugin = classDeclaration.getAnnotationsByType(JsonRepo::class).first().pluginPropertyPath
            val keyType = classDeclaration.getDeclaredProperties().filter { it.isAnnotationPresent(ID::class) }.first().type

            repos.add(generateJsonRepo(plugin,
                className(classDeclaration.asType(emptyList())).lowercase(),
                className(keyType.resolve()),
                className(classDeclaration.asType(emptyList()))))
        }
    }



}



fun replaceKotlinDefType(s: String): String = if(s.startsWith("kotlin.")) s.replace("kotlin.", "") else s
@OptIn(KotlinPoetKspPreview::class)
fun className(type: KSType): String = replaceKotlinDefType(type.toClassName().canonicalName)