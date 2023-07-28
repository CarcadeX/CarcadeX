package me.redtea.carcadex.kotlin.extensions

import me.redtea.carcadex.message.container.Messages
import me.redtea.carcadex.message.verifier.MessageVerifier
import org.bukkit.plugin.Plugin
import java.io.File

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
        if(plugin == null) throw RuntimeException("Missed plugin")
        return if(file == null) {
            return (if(legacy) Messages.legacy(fileName ?: "messages.yml", plugin!!) else Messages.of(fileName ?: "messages.yml", plugin!!)).also { m -> verifier?.let { m.verifier(it) } }
        } else return (if(legacy) Messages.legacy(file!!, plugin!!) else Messages.of(file!!, plugin!!)).also { m -> verifier?.let { m.verifier(it) } }
    }
}
