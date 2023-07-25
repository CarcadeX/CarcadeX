package me.redtea.carcadex.message.model.impl;

import me.redtea.carcadex.message.model.AbstractMessage;
import me.redtea.carcadex.message.model.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class ComponentMessage extends AbstractMessage {
    private final MiniMessage mm;
    private Plugin plugin;

    public ComponentMessage(List<String> unparsed, Plugin plugin) {
        super(unparsed, plugin);
        this.plugin = plugin;
        mm = MiniMessage.builder().build();
    }

    @Override
    protected Component parse(String unparsed) {
        return mm.deserialize(unparsed);
    }

    @Override
    public @NotNull Message replaceAll(@NotNull String from, @NotNull String to) {
        return new ComponentMessage(unparsed.stream().map(it -> it.replaceAll(from, to)).collect(Collectors.toList()), plugin);
    }
}
