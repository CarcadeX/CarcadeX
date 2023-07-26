package me.redtea.carcadex.message.container.impl;

import me.redtea.carcadex.message.container.Messages;
import me.redtea.carcadex.message.container.util.SectionProvider;
import me.redtea.carcadex.message.model.Message;
import me.redtea.carcadex.message.factory.MessageFactory;
import me.redtea.carcadex.message.model.impl.NullMessage;
import me.redtea.carcadex.message.verifier.MessageVerifier;
import me.redtea.carcadex.reload.parameterized.ParameterizedReloadable;
import me.redtea.carcadex.reload.parameterized.container.ReloadContainer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;

public class MessagesImpl extends ParameterizedReloadable implements Messages {
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

    public MessagesImpl(@NotNull SectionProvider sectionProvider, MessageFactory factory) {
        this(sectionProvider.get(), factory);
    }

    @Override
    public @NotNull Message get(@NotNull String key) {
        if(!has(key)) {
            if(verifier != null) {
                Optional<Object> def = verifier.fromDefault(key);
                if(def.isPresent()) {
                    if(def.get() instanceof List) {
                        messages.put(key, factory.message((List<String>) def.get()));
                    } else put(key, factory.message(def.get().toString()));
                    return messages.get(key);
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

    @Override
    public void init(ReloadContainer container) {
        if(container == null) return;
        ConfigurationSection config = null;
        if(container.contains("section")) config = container.get("section");
        else if(container.contains("provider")) config = container.<SectionProvider>get("provider").get();
        else if(container.contains("file")) config = YamlConfiguration.loadConfiguration(container.<File>get("file"));
        messages = fromConfigurationToMap(config);
    }

    @Override
    public void close() {
        messages.clear();
    }
}
