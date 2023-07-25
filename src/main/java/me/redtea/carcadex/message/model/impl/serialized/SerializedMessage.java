package me.redtea.carcadex.message.model.impl.serialized;

import me.redtea.carcadex.message.model.AbstractMessage;
import me.redtea.carcadex.message.model.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class SerializedMessage extends AbstractMessage {
    private final ComponentSerializer<Component, TextComponent, String> serializer;

    public SerializedMessage(List<String> unparsed, ComponentSerializer<Component, TextComponent, String> serializer) {
        super(unparsed);
        this.serializer = serializer;
    }

    @Override
    protected Component parse(String unparsed) {
        return serializer.deserialize(unparsed);
    }

    @Override
    public @NotNull Message replaceAll(@NotNull String from, @NotNull String to) {
        return new SerializedMessage(unparsed.stream().map(it -> it.replaceAll(from, to)).collect(Collectors.toList()), serializer);
    }
}
