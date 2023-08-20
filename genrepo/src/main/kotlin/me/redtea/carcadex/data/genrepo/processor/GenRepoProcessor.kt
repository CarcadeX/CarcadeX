package me.redtea.carcadex.data.genrepo.processor

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.validate
import me.redtea.carcadex.data.genrepo.annotations.ExposedRepo
import me.redtea.carcadex.data.genrepo.exposed.ExposedVisitor

class GenRepoProcessor(private val codeGenerator: CodeGenerator,
                       private val logger: KSPLogger) : SymbolProcessor {
    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val files = resolver.getAllFiles().filter { it.declarations.any { it is KSFunctionDeclaration && it.validate()
                && it.isAnnotationPresent(ExposedRepo::class)} }

        files.let { files -> files.forEach {
                it.accept(ExposedVisitor(codeGenerator, logger), Unit)
            }
        }
        return files.filter { it.validate() }.toList()
    }

}