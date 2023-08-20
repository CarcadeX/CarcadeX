package me.redtea.genrepojsonbukkit.processor

import me.redtea.carcadex.data.kotlinextensions.repo
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bukkit.plugin.Plugin

val json = Json

@Serializable
data class User(
    val id: Int,
    val online: Boolean,
    var col: Collection<String>
)


