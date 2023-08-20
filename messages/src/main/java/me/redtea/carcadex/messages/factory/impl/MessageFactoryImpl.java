package me.redtea.carcadex.messages.factory.impl;

import com.google.common.collect.Lists;
import me.redtea.carcadex.messages.model.Message;
import me.redtea.carcadex.messages.utils.MD5ColorUtils;
import me.redtea.carcadex.messages.factory.MessageFactory;
import me.redtea.carcadex.messages.model.impl.ComponentMessage;
import me.redtea.carcadex.messages.model.impl.serialized.impl.LegacyMessage;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.stream.Collectors;

public class MessageFactoryImpl implements MessageFactory {
    private final Plugin plugin;

    public MessageFactoryImpl(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Message message(List<String> unparsed) {
        if(unparsed == null || unparsed.isEmpty()) return nullMessage();
        return new ComponentMessage(unparsed, plugin);
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
        return new LegacyMessage(unparsed, plugin);
    }

    @Override
    public Message legacyMessage(String msg) {
        return legacyMessage(Lists.newArrayList(msg));
    }
}
