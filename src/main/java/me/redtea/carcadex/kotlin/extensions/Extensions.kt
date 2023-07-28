package me.redtea.carcadex.kotlin.extensions

import me.redtea.carcadex.message.container.Messages
import me.redtea.carcadex.message.model.Message
import me.redtea.carcadex.message.verifier.MessageVerifier
import me.redtea.carcadex.repo.MutableRepo
import me.redtea.carcadex.repo.Repo
import me.redtea.carcadex.repo.builder.RepoBuilder
import me.redtea.carcadex.repo.builder.exception.NotConfiguredException
import me.redtea.carcadex.schema.file.impl.serialize.SerializeSchemaStrategy
import me.redtea.carcadex.serializer.CommonSerializer
import me.redtea.carcadex.utils.MaterialUtils
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.event.Cancellable
import org.bukkit.plugin.Plugin
import java.io.File
import java.nio.file.Files

val console = Bukkit.getConsoleSender()

fun Cancellable.cancel() {
    this.isCancelled = true
}

fun consoleCommand(command: String) = Bukkit.dispatchCommand(console, command)

fun CommandSender.send(message: Message) = message.send(this)

fun matchMaterial(material: String): Material = MaterialUtils.getMaterialFromString(material)

fun Plugin.print(s: String) = logger.info(s)
fun Plugin.print(a: Any) = print(a.toString())

