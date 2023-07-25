package me.redtea.carcadex.message.model.impl.serialized.impl;

import me.redtea.carcadex.message.model.impl.serialized.SerializedMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.List;

public class LegacyMessage extends SerializedMessage {
    public LegacyMessage(List<String> unparsed) {
        super(unparsed, LegacyComponentSerializer.builder().build());
    }
}
