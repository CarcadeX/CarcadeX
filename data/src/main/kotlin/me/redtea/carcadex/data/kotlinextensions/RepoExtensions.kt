package me.redtea.carcadex.data.kotlinextensions

import me.redtea.carcadex.data.repo.MutableRepo
import me.redtea.carcadex.data.repo.builder.RepoBuilder
import me.redtea.carcadex.data.repo.builder.exception.NotConfiguredException
import me.redtea.carcadex.data.schema.file.impl.serialize.SerializeSchemaStrategy
import me.redtea.carcadex.data.serializer.CommonSerializer
import java.io.File

fun <K, V>repo(folder: File, init: RepoBuilderKt<K, V>.() -> Unit): MutableRepo<K, V> {
    val builder = decorator(RepoBuilder.get<K, V>())
    builder.folder(folder)
    builder.init()
    builder.wrapSchema()
    return builder.build()
}

fun <K, V>repo(init: RepoBuilderKt<K, V>.() -> Unit): MutableRepo<K, V> {
    val builder = decorator(RepoBuilder.get<K, V>())
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
        schema(
            SerializeSchemaStrategy<K, V>(
            folder,
            object : CommonSerializer<V> {
                override fun serialize(v: V): String = (save
                    ?: throw NotConfiguredException("missed save function"))(
                    v
                )

                override fun deserialize(s: String): V = (load
                    ?: throw NotConfiguredException("missed save function"))(
                    s
                )
            }, fileExt ?: ""
        )
        )
        return this
    }
}