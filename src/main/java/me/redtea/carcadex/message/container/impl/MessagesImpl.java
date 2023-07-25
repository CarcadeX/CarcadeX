package me.redtea.carcadex.message.container.impl;

import me.redtea.carcadex.message.container.Messages;
import me.redtea.carcadex.message.container.util.SectionProvider;
import me.redtea.carcadex.message.model.Message;
import me.redtea.carcadex.message.factory.MessageFactory;
import me.redtea.carcadex.message.verifier.MessageVerifier;
import me.redtea.carcadex.reload.parameterized.ParameterizedReloadable;
import me.redtea.carcadex.reload.parameterized.container.ReloadContainer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class MessagesImpl extends ParameterizedReloadable implements Messages {
    private Map<String, Message> messages;

    private MessageFactory factory = MessageFactory.instance;

    private final Message NULL_MESSAGE = factory.nullMessage();

    private ConfigurationSection section;

    @Nullable
    private MessageVerifier verifier;

    public MessagesImpl() {
    }

    public MessagesImpl(@NotNull ConfigurationSection section) {
        this.section = section;
    }

    public MessagesImpl(@NotNull File file) {
        this(YamlConfiguration.loadConfiguration(file));
    }

    public MessagesImpl(@NotNull SectionProvider sectionProvider) {
        this(sectionProvider.get());
    }

    @Override
    public @NotNull Message get(@NotNull String key) {
        if(!has(key)) {
            if(verifier != null) {
                Optional<String> def = verifier.fromDefault(key);
                if(def.isPresent()) {
                    messages.put(key, factory.message(def.get()));
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
    public Messages factory(@NotNull MessageFactory messageFactory) {
        this.factory = messageFactory;
        return this;
    }

    @Override
    public Messages parse() {
        if(section != null) messages = fromConfigurationToMap(section);
        return this;
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
                Message message = null;
                if (section.isString(key)) {
                    message = factory.message(section.getString(key));
                } else if (section.isList(key)) {
                    message = factory.message(section.getStringList(key));
                }

                if (message != null) {
                    data.put(key, message);
                }
            }
        });

        return data;
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
