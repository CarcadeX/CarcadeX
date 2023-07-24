package me.redtea.carcadex.message.factory.impl;

import com.google.common.collect.Lists;
import me.redtea.carcadex.message.factory.MessageFactory;
import me.redtea.carcadex.message.model.Message;
import me.redtea.carcadex.message.model.impl.ComponentMessage;
import me.redtea.carcadex.message.model.impl.serialized.impl.LegacyMessage;
import me.redtea.carcadex.message.utils.MD5ColorUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MessageFactoryImpl implements MessageFactory {
    @Override
    public Message message(List<String> unparsed) {
        if(unparsed == null || unparsed.isEmpty()) return nullMessage();
        return new ComponentMessage(unparsed);
    }

    @Override
    public Message message(String msg) {
        return message(Lists.newArrayList(msg));
    }

    @Override
    public Message legacyMessage(List<String> unparsed) {
        if(unparsed == null || unparsed.isEmpty()) return nullMessage();
        if(unparsed.stream().anyMatch(it -> it.contains("&"))) unparsed =
                unparsed.stream()
                        .map(MD5ColorUtils::translateHexColorCodes)
                        .collect(Collectors.toList());
        return new LegacyMessage(unparsed);
    }

    @Override
    public Message legacyMessage(String msg) {
        return legacyMessage(Lists.newArrayList(msg));
    }
}
