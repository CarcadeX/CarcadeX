package me.redtea.carcadex.message.factory.impl;

import me.redtea.carcadex.message.factory.MessageFactory;
import me.redtea.carcadex.message.model.Message;
import me.redtea.carcadex.message.model.impl.ComponentMessage;
import me.redtea.carcadex.message.model.impl.serialized.impl.LegacyMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageFactoryImpl implements MessageFactory {
    @Override
    public Message message(List<String> unparsed) {
        if(unparsed == null || unparsed.isEmpty()) return nullMessage();
        return new ComponentMessage(unparsed);
    }

    @Override
    public Message message(String msg) {
        return message(new ArrayList<>(Collections.singleton(msg)));
    }

    @Override
    public Message legacyMessage(List<String> unparsed) {
        if(unparsed == null || unparsed.isEmpty()) return nullMessage();
        return new LegacyMessage(unparsed);
    }

    @Override
    public Message legacyMessage(String msg) {
        return legacyMessage(new ArrayList<>(Collections.singleton(msg)));
    }
}
