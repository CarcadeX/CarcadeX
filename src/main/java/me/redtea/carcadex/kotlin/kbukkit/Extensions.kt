package me.redtea.carcadex.kotlin.kbukkit

import me.redtea.carcadex.message.container.Messages
import me.redtea.carcadex.message.model.Message
import me.redtea.carcadex.message.verifier.MessageVerifier
import me.redtea.carcadex.repo.MutableRepo
import me.redtea.carcadex.repo.Repo
import me.redtea.carcadex.schema.SchemaStrategy
import me.redtea.carcadex.schema.impl.serialize.SerializeSchemaStrategy
import me.redtea.carcadex.serializer.CommonSerializer
import me.redtea.carcadex.utils.MaterialUtils
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.event.Cancellable
import org.bukkit.plugin.Plugin
import org.slf4j.Logger
import java.io.File
import java.nio.file.Path

val console = Bukkit.getConsoleSender()

fun Cancellable.cancel() {
    this.isCancelled = true
}

fun consoleCommand(command: String) = Bukkit.dispatchCommand(console, command)

fun messages(init: MessagesBuilder.() -> Unit): Messages {
    val builder = MessagesBuilder()
    builder.init()
    return builder.build()
}

class MessagesBuilder(var legacy: Boolean = false,
                      var plugin: Plugin? = null,
                      var fileName: String? = null,
                      var verifier: MessageVerifier? = null,
                      var file: File? = null) {
    fun build(): Messages {
        if(plugin == null) throw ParamMissedException("Missed plugin")
        return if(file == null) {
            return (if(legacy) Messages.legacy(fileName ?: "messages.yml", plugin!!) else Messages.of(fileName ?: "messages.yml", plugin!!)).also { m -> verifier?.let { m.verifier(it) } }
        } else return (if(legacy) Messages.legacy(file!!, plugin!!) else Messages.of(file!!, plugin!!)).also { m -> verifier?.let { m.verifier(it) } }
    }
}

fun <K, V>repo(init: RepoBuilder<K, V>.() -> Unit): MutableRepo<K, V> {
    val builder = RepoBuilder<K, V>()
    builder.init()
    return builder.build()
}

fun <K, V> RepoBuilder<K, V>.save(l: (V) -> String) {
    this.save = l
}

fun <K, V> RepoBuilder<K, V>.load(l: ((String) -> V)) {
    this.load = l
}

class RepoBuilder<K, V>(
    var plugin: Plugin? = null,
    var autoSave: Long? = null,

    var folder: Path? = null,

    var logger: Logger? = null,

    var sync: Boolean? = null,
    var concurrent: Boolean? = null,

    var save: ((V) -> String)? = null,
    var load: ((String) -> V)? = null,
    //or
    var schemaStrategy: SchemaStrategy<K, V>? = null,

    var folderName: String = "carcadex-data",
) {


    fun build(): MutableRepo<K, V> {
        val strategy = schemaStrategy ?: SerializeSchemaStrategy(folder ?:
            File((plugin ?: throw ParamMissedException("missed plugin")).dataFolder, folderName).toPath(),
            object : CommonSerializer<V> {
                override fun serialize(t: V): String = (save ?: throw ParamMissedException("missed save function"))(t)
                override fun deserialize(s: String?): V = (load ?: throw ParamMissedException("missed load function"))(s!!)
            });
        val builder = Repo.builder<K, V>()
            .plugin(plugin)
            .logging(logger)
            .schema(strategy)
        autoSave?.let { builder.autoSave(it) }
        sync?.let { if(it) builder.sync() }
        concurrent?.let { if(it) builder.concurrent() }
        return builder.build();
    }
}

class ParamMissedException(s: String) : RuntimeException(s)

fun CommandSender.send(message: Message) = message.send(this)
fun CommandSender.sendMessage(message: Message) = message.send(this)

fun matchMaterial(material: String): Material = MaterialUtils.getMaterialFromString(material)