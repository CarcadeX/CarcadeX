package me.redtea.carcadex.message.model.impl;

import me.redtea.carcadex.message.model.AbstractMessage;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.ArrayList;
import java.util.List;

public class ComponentMessage extends AbstractMessage {
    private final MiniMessage mm;

    public ComponentMessage(List<String> unparsed) {
        super(unparsed);
        mm = MiniMessage.miniMessage();
    }

    @Override
    protected void parse() {
        if(parsed != null) return;
        parsed = new ArrayList<>();
        for(String s : unparsed) {
            parsed.add(mm.deserialize(s));
        }
    }
}
