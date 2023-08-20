package me.redtea.carcadex.messages.kotlin

import dev.triumphteam.gui.builder.item.ItemBuilder
import me.redtea.carcadex.messages.model.Message

fun ItemBuilder.name(message: Message) = this.name(message.asComponent())

fun ItemBuilder.lore(message: Message) = this.lore(message.asComponentList())