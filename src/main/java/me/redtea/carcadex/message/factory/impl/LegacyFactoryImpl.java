package me.redtea.carcadex.message.factory.impl;

import com.google.common.collect.Lists;
import me.redtea.carcadex.message.model.Message;
import me.redtea.carcadex.message.utils.MD5ColorUtils;
import me.redtea.carcadex.message.factory.MessageFactory;
import me.redtea.carcadex.message.model.impl.serialized.impl.LegacyMessage;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.stream.Collectors;

public class LegacyFactoryImpl implements MessageFactory {
    private final Plugin plugin;

    public LegacyFactoryImpl(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Message message(List<String> unparsed) {
        if(unparsed == null || unparsed.isEmpty()) return nullMessage();
        if(unparsed.stream().anyMatch(it -> it.contains("&"))) unparsed =
                unparsed.stream()
                        .map(MD5ColorUtils::translateHexColorCodes)
                        .collect(Collectors.toList());
        return new LegacyMessage(unparsed, plugin);
    }

    @Override
    public Message message(String msg) {
        if(msg == null) return nullMessage();
        return new LegacyMessage(Lists.newArrayList(msg), plugin);
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
