package me.redtea.carcadex.kotlin.message.triumphgui

import dev.triumphteam.gui.builder.item.ItemBuilder
import me.redtea.carcadex.message.model.Message

fun ItemBuilder.name(message: Message) = this.name(message.asComponent())

fun ItemBuilder.lore(message: Message) = this.lore(message.asComponentList())