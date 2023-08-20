package me.redtea.carcadex.genref.plugin

import br.com.devsrsouza.kotlinbukkitapi.architecture.KotlinPlugin
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.writeTo
import me.redtea.carcadex.genref.plugin.annotations.OnDisable
import me.redtea.carcadex.genref.plugin.annotations.Plugin

@OptIn(KotlinPoetKspPreview::class)
class PluginVisitor(private val codeGenerator: CodeGenerator,
                       private val logger: KSPLogger
) : KSVisitorVoid() {

    var funcs: MutableList<FunSpec> = mutableListOf()

    override fun visitFile(file: KSFile, data: Unit) {
        file.declarations.filter { it is KSFunctionDeclaration }.forEach { it.accept(this, Unit) }

        val packageName = file.packageName.asString()
        val className = file.fileName.replace(
            ".kt",
            ""
        ) + "Plugin"

        val fileSpec = FileSpec.builder(packageName, className).apply {
            addProperty(
                PropertySpec.builder(
                    "plugin",
                    ClassName(packageName, className)
                )
                    .mutable(true)
                    .setter(FunSpec.builder("set()").addModifiers(KModifier.PRIVATE).build())
                    .addModifiers(
                        listOf(
                            KModifier.LATEINIT
                        )
                    )
                    .build()
            )
            addType(
                TypeSpec.Companion.classBuilder(className).primaryConstructor(
                    FunSpec.constructorBuilder().build()
                )
                    .addFunctions(funcs)
                    .superclass(KotlinPlugin::class)
                    .build()
            )
        }.build()
        try {
            fileSpec.writeTo(codeGenerator, true)
        } catch (ignored: FileAlreadyExistsException) {
        }
    }

    @OptIn(KspExperimental::class)
    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
        if (function.getAnnotationsByType(OnDisable::class).any()) disable(function)

        if(function.getAnnotationsByType(Plugin::class).any()) enable(function)


        function.returnType!!.accept(this, Unit)
    }

    fun disable(function: KSFunctionDeclaration) {
        funcs.add(FunSpec.builder("onPluginDisable")
            .addCode(CodeBlock.of("""
                            ${function.simpleName.getShortName()}()
                        """.trimIndent()))
            .returns(Unit::class)
            .addModifiers(KModifier.OVERRIDE)
            .build())

    }

    fun enable(function: KSFunctionDeclaration) {
        funcs.add(FunSpec.builder("onPluginEnable")
            .addCode(CodeBlock.of("""
                            plugin = this
                            ${function.simpleName.getShortName()}()
                            
                        """.trimIndent()))
            .returns(Unit::class)
            .addModifiers(KModifier.OVERRIDE)
            .build())
    }


}


