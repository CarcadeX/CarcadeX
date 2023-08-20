package me.redtea.carcadex.data.genrepo.exposed

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.*
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo
import generateExposedRepo
import me.redtea.carcadex.data.genrepo.annotations.ExposedDatabase
import me.redtea.carcadex.data.genrepo.annotations.ExposedRepo
import me.redtea.exposedgenerator.annotations.ExposedTable
import me.redtea.exposedgenerator.annotations.ID
import java.io.OutputStreamWriter

class ExposedVisitor(private val codeGenerator: CodeGenerator,
                     private val logger: KSPLogger
) : KSVisitorVoid() {
    var database = "org.jetbrains.exposed.sql.Database"
    var repos = mutableListOf<String>()

    override fun visitFile(file: KSFile, data: Unit) {
        val packageName = file.packageName.asString()
        repos.add(
            """
package $packageName

import me.redtea.carcadex.data.schema.AbstractSchemaStrategy
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils
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
    override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: Unit) {
        if(property.isAnnotationPresent(ExposedDatabase::class)) {
            database = (property.packageName.asString() + "." + property.simpleName)
        }
    }

    @OptIn(KspExperimental::class)
    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        if(classDeclaration.isAnnotationPresent(ExposedRepo::class)) {
            database = classDeclaration.getAnnotationsByType(ExposedRepo::class).first().databasePropertyPath
            val fromAnnotation = if(classDeclaration.isAnnotationPresent(ExposedTable::class))classDeclaration.getAnnotationsByType(ExposedTable::class).first().name else ""
            val tableName = if(fromAnnotation != "") fromAnnotation else (if(classDeclaration.simpleName.asString().endsWith("s")) classDeclaration.simpleName.asString() + "es" else classDeclaration.simpleName.asString() + "s")
            val keyType = classDeclaration.getDeclaredProperties().filter { it.isAnnotationPresent(ID::class) }.first().type

            repos.add(generateExposedRepo(classDeclaration.packageName.asString(),
                className(classDeclaration.asType(emptyList())).lowercase(),
                className(keyType.resolve()),
                className(classDeclaration.asType(emptyList())),
                database,
                tableName))
        }
    }



}



fun replaceKotlinDefType(s: String): String = if(s.startsWith("kotlin.")) s.replace("kotlin.", "") else s
@OptIn(KotlinPoetKspPreview::class)
fun className(type: KSType): String = replaceKotlinDefType(type.toClassName().canonicalName)