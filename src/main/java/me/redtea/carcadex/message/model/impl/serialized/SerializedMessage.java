package me.redtea.carcadex.message.model.impl.serialized;

import me.redtea.carcadex.message.model.AbstractMessage;
import me.redtea.carcadex.message.model.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class SerializedMessage extends AbstractMessage {
    private final ComponentSerializer<Component, TextComponent, String> serializer;
    private final Plugin plugin;

    public SerializedMessage(List<String> unparsed, ComponentSerializer<Component, TextComponent, String> serializer, Plugin plugin) {
        super(unparsed, plugin);
        this.serializer = serializer;
        this.plugin = plugin;
    }

    @Override
    protected Component parse(String unparsed) {
        return serializer.deserialize(unparsed);
    }

    @Override
    public @NotNull Message replaceAll(@NotNull String from, @NotNull String to) {
        return new SerializedMessage(unparsed.stream().map(it -> it.replaceAll(from, to)).collect(Collectors.toList()), serializer, plugin);
    }
}
