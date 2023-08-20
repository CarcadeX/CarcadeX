package me.redtea.carcadex.data.bukkitextensions.kotlinextextensions

import me.redtea.carcadex.data.kotlinextensions.RepoBuilderKt
import me.redtea.carcadex.data.kotlinextensions.repo
import me.redtea.carcadex.data.repo.MutableRepo
import org.bukkit.plugin.Plugin
import java.io.File

inline fun <K, reified V>repo(plugin: Plugin, noinline init: RepoBuilderKt<K, V>.() -> Unit): MutableRepo<K, V>
        = repo(V::class.java.simpleName.lowercase().let { if (it.endsWith("s")) it + "es" else it + "s" }, plugin, init)

fun <K, V>repo(fileName: String, plugin: Plugin, init: RepoBuilderKt<K, V>.() -> Unit): MutableRepo<K, V> {
    return repo(File(plugin.dataFolder, fileName), init)
}