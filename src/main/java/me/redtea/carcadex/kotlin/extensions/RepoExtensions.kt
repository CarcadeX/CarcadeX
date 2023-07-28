package me.redtea.carcadex.kotlin.extensions

import me.redtea.carcadex.repo.MutableRepo
import me.redtea.carcadex.repo.Repo
import me.redtea.carcadex.repo.builder.RepoBuilder
import me.redtea.carcadex.repo.builder.exception.NotConfiguredException
import me.redtea.carcadex.schema.file.impl.serialize.SerializeSchemaStrategy
import me.redtea.carcadex.serializer.CommonSerializer
import org.bukkit.plugin.Plugin
import java.io.File



inline fun <K, reified V>repo(plugin: Plugin, noinline init: RepoBuilderKt<K, V>.() -> Unit): MutableRepo<K, V>
    = repo(V::class.java.simpleName.lowercase().let { if (it.endsWith("s")) it + "es" else it + "s" }, plugin, init)

fun <K, V>repo(fileName: String, plugin: Plugin, init: RepoBuilderKt<K, V>.() -> Unit): MutableRepo<K, V> {
    return repo(File(plugin.dataFolder, fileName), init)
}

fun <K, V>repo(folder: File, init: RepoBuilderKt<K, V>.() -> Unit): MutableRepo<K, V> {
    val builder = decorator(Repo.builder<K, V>())
    builder.folder(folder)
    builder.init()
    builder.wrapSchema()
    return builder.build()
}

fun <K, V>repo(init: RepoBuilderKt<K, V>.() -> Unit): MutableRepo<K, V> {
    val builder = decorator(Repo.builder<K, V>())
    builder.init()
    builder.wrapSchema()
    return builder.build()
}

fun <K, V> RepoBuilderKt<K, V>.save(lambda: (V) -> String) {
    this.save = lambda
}

fun <K, V> RepoBuilderKt<K, V>.load(lambda: ((String) -> V)) {
    this.load = lambda
}

private fun <K, V>decorator(repoBuilder: RepoBuilder<K, V>): RepoBuilderKt<K, V> = RepoBuilderKt(repoBuilder)
class RepoBuilderKt<K, V>(private val repoBuilder: RepoBuilder<K, V>) : RepoBuilder<K, V> by repoBuilder {
    var save: ((V) -> String)? = null
    var load: ((String) -> V)? = null
    var fileExt: String? = null

    private var folder: File? = null
    override fun folder(folder: File?): RepoBuilder<K, V> {
        this.folder = folder
        return repoBuilder.folder(folder)
    }

    fun wrapSchema(): RepoBuilderKt<K, V> {
        if(save == null || load == null) return this
        schema(SerializeSchemaStrategy<K, V>(
            folder,
            object : CommonSerializer<V> {
                override fun serialize(v: V): String = (save ?: throw NotConfiguredException("missed save function"))(v)
                override fun deserialize(s: String): V = (load ?: throw NotConfiguredException("missed save function"))(s)
            }, fileExt ?: ""))
        return this
    }
}