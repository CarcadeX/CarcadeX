package me.redtea.carcadex.message.model.impl;

import me.redtea.carcadex.message.model.AbstractMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.List;

public class ComponentMessage extends AbstractMessage {
    private final MiniMessage mm;

    public ComponentMessage(List<String> unparsed) {
        super(unparsed);
        mm = MiniMessage.builder().build();
    }

    @Override
    protected Component parse(String unparsed) {
        return mm.deserialize(unparsed);
    }
}
