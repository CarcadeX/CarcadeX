package me.redtea.carcadex.message.model.impl.serialized;

import me.redtea.carcadex.message.model.AbstractMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.ComponentSerializer;

import java.util.ArrayList;
import java.util.List;

public class SerializedMessage extends AbstractMessage {
    private final ComponentSerializer<Component, TextComponent, String> serializer;

    public SerializedMessage(List<String> unparsed, ComponentSerializer<Component, TextComponent, String> serializer) {
        super(unparsed);
        this.serializer = serializer;
    }

    @Override
    protected void parse() {
        if(parsed != null) return;
        parsed = new ArrayList<>();
        for(String s : unparsed) {
            parsed.add(serializer.deserialize(s));
        }
    }
}
