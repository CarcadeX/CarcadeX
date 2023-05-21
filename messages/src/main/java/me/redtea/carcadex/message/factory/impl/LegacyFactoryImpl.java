package me.redtea.carcadex.message.factory.impl;

import com.google.common.collect.Lists;
import me.redtea.carcadex.message.factory.MessageFactory;
import me.redtea.carcadex.message.model.Message;
import me.redtea.carcadex.message.model.impl.serialized.impl.LegacyMessage;

import java.util.List;

public class LegacyFactoryImpl implements MessageFactory {
    @Override
    public Message message(List<String> unparsed) {
        if(unparsed == null || unparsed.isEmpty()) return nullMessage();
        return new LegacyMessage(unparsed);
    }

    @Override
    public Message message(String msg) {
        if(msg == null) return nullMessage();
        return new LegacyMessage(Lists.newArrayList(msg));
    }

    @Override
    public Message legacyMessage(List<String> unparsed) {
        return message(unparsed);
    }

    @Override
    public Message legacyMessage(String msg) {
        return message(Lists.newArrayList(msg));
    }
}
