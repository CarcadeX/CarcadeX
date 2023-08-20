package me.redtea.carcadex.messages.container.impl;

import me.redtea.carcadex.messages.container.Messages;
import me.redtea.carcadex.messages.model.Message;
import me.redtea.carcadex.messages.factory.MessageFactory;
import me.redtea.carcadex.messages.model.impl.NullMessage;
import me.redtea.carcadex.messages.verifier.MessageVerifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;

public class MessagesImpl implements Messages {
    private Map<String, Message> messages;

    private MessageFactory factory;

    private final Message NULL_MESSAGE;

    private ConfigurationSection section;

    @Nullable
    private MessageVerifier verifier;

    public MessagesImpl() {
        NULL_MESSAGE = new NullMessage();
    }

    public MessagesImpl(@NotNull ConfigurationSection section, MessageFactory factory) {
        this.section = section;
        this.factory = factory;
        NULL_MESSAGE = factory.nullMessage();
        parse();
    }

    public MessagesImpl(@NotNull File file, MessageFactory factory) {
        this(YamlConfiguration.loadConfiguration(file), factory);
    }

    @Override
    public @NotNull Message get(@NotNull String key) {
        if(!has(key) || messages.get(key) == null) {
            if(verifier != null) {
                Optional<Object> def = verifier.fromDefault(key);
                if(def.isPresent()) {
                    Message message = def.get() instanceof List ? factory.message((List<String>) def.get())
                            : factory.message(def.get().toString());
                    messages.put(key, message);
                    return message;
                }
                return NULL_MESSAGE;
            }
            return NULL_MESSAGE;
        }
        return messages.get(key) == null ? NULL_MESSAGE : messages.get(key);
    }

    @Override
    public @NotNull Message put(@NotNull String key, @NotNull Message message) {
        if(!has(key)) return NULL_MESSAGE;
        return Objects.requireNonNull(messages.put(key, message));
    }

    @Override
    public boolean has(String key) {
        if(key == null) return false;
        return messages.containsKey(key);
    }

    @Override
    public void reload(@NotNull ConfigurationSection section) {
        messages = fromConfigurationToMap(section);
    }

    @Override
    public void factory(@NotNull MessageFactory messageFactory) {
        this.factory = messageFactory;
    }

    @Override
    public void parse() {
        if(section != null) messages = fromConfigurationToMap(section);
    }

    @Override
    public void verifier(MessageVerifier verifier) {
        this.verifier = verifier;
    }



    private Map<String, Message> fromConfigurationToMap(@NotNull ConfigurationSection section) {
        Map<String, Message> data = new HashMap<>();
        section.getKeys(false).forEach(key -> {
            if (section.isConfigurationSection(key)) {
                Map<String, Message> newMessages = fromConfigurationToMap(Objects.
                        requireNonNull(section.getConfigurationSection(key)));
                newMessages.forEach((keyMessage, message) -> data.put(key + "." + keyMessage, message));
            } else {
                fromSection(section, key).ifPresent(value -> data.put(key, value));
            }
        });

        return data;
    }

    private Optional<Message> fromSection(@NotNull ConfigurationSection section, String key) {
        Message message = null;
        if (section.isString(key)) {
            message = factory.message(section.getString(key));
        } else if (section.isList(key)) {
            message = factory.message(section.getStringList(key));
        }
        return Optional.ofNullable(message);
    }

}
