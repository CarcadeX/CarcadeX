package me.redtea.carcadex.message.container;

import me.redtea.carcadex.message.factory.impl.MessageFactoryImpl;
import me.redtea.carcadex.message.model.Message;
import me.redtea.carcadex.message.container.impl.MessagesImpl;
import me.redtea.carcadex.message.factory.MessageFactory;
import me.redtea.carcadex.message.factory.impl.LegacyFactoryImpl;
import me.redtea.carcadex.message.model.impl.NullMessage;
import me.redtea.carcadex.message.verifier.MessageVerifier;
import me.redtea.carcadex.message.verifier.impl.FileMessageVerifier;
import me.redtea.carcadex.reload.Reloadable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;

/**
 * <h1>Container of messages</h1>
 * <h2>Create container by plugin with file messages.yml</h2>
 * <h3>Step 1. Create file messages.yml in resources folder (in jar of plugin)</h3>
 * <p>Write in file your messages in YAML format. Use MiniMessages format for color messages. <br>
 * See <a href="https://docs.advntr.dev/minimessage/format.html">https://docs.advntr.dev/minimessage/format.html</a></p>
 * <p>For example I will be use this content:</p>
 * <pre>
 * {@code
 * error: "<red>Error!"
 * someRoot:
 *      message1: "<green>Message"
 *      message2:
 *          - "<green>Message start!"
 *          - "<green>Message end!"
 *
 * }
 * </pre>
 * <h3>Step 2. Load Messages container in your onEnable method</h3>
 * <pre>
 * {@code
 * Messages messages = Messages.of(this);
 * }
 * </pre>
 * <h3>Step 3. Done! Now you can use the messages variable to manage messages.</h3>
 * <h2>Create container by plugin with custom file</h2>
 * <p>Everything is the same, the same, only in jar create a file with the name you need (or several such files)
 * and change OnEnable like this:</p>
 * <pre>
 * {@code
 * Messages messages = Messages.of(this, "YOURFILENAME.yml");
 * }
 * </pre>
 * <h2>Create container by ConfigurationSection</h2>
 * <p>Here are a couple of examples of how this can be done:</p>
 * <pre>
 * {@code
 * Messages messages = Messages.of(getConfig().getConfigurationSection("messages"));
 *
 * //or so
 *
 * ConfigurationSection section = ...;
 * Messages messages = Messages.of(section);
 * }
 * </pre>
 * Reloadable Container:
 * section - ConfigurationSection with messages
 * file - File with messages
 * SectionProvider - provider of section with messages
 * @since 1.0.0
 * @author itzRedTea
 */
public interface Messages extends Reloadable {
    /**
     * <h1>Get message from container by key/yaml path</h1>
     *
     * <h2>For example</h2>
     * <p><b>Yaml structure:</b></p>
     * <pre>
     * {@code
     * error: "<red>Error!"
     * someRoot:
     *      message1: "<green>Message"
     *      message2:
     *          - "<green>Message start!"
     *          - "<green>Message end!"
     *
     * }
     * </pre>
     *
     * <p><b>Getting messages in code:</b></p>
     * <pre>
     * {@code
     * Messages messages = ...; //Init Messages
     * CommandSender sender = ...; //Getting CommandSender
     * messages.get("error").send(sender); //Getting error message
     * message.get("someRoot.message1").send(sender); //Getting someRoot.message1 message
     * message.get("someRoot.message2").send(sender); //Getting someRoot.message2 message
     * }
     * </pre>
     *
     * @param key key of message
     * @return Message
     * @see Message
     */
    @NotNull Message get(@NotNull String key);

    /**
     * <h1>Put message to container</h1>
     * @param key key/yaml path to message
     * @param message message to mut
     * @return putted message
     * @see Message
     */
    @NotNull Message put(@NotNull String key, @NotNull Message message);

    /**
     * @param key key/yaml path to message
     * @return is there a message with such a key in the container?
     */
    boolean has(String key);

    /**
     * <h1>Reload container with messages in section from args.</h1>
     * @param section - ConfigurationSection with messages
     */
    void reload(@NotNull ConfigurationSection section);

    /**
     * <h1>Sets custom message parse factory.</h1>
     */
    void factory(@NotNull MessageFactory messageFactory);

    void parse();

    void verifier(MessageVerifier verifier);

    /**
     * <h1>Create container using configuration section</h1>
     * Create container using messages from configuration section.
     * <pre>
     * {@code
     * ConfigurationSection section = ...;
     * Messages messages = Messages.of(section);
     * }
     * </pre>
     * @param section - configuration section with messages
     * @return container with messages
     */
    static Messages of(ConfigurationSection section, MessageFactory factory) {
        return new MessagesImpl(section, factory);
    }

    /**
     * <h1>Create container using current plugin and custom filename</h1>
     * Create container using file with your name from jar entry by default.
     * A new file in plugin folder will be created with content from your file.
     * <pre>
     * {@code
     * Messages messages = Messages.of(this, "YOURFILENAME.yml");
     * }
     * </pre>
     * @param plugin - plugin that creates a container
     * @return container with messages
     */
    static Messages of(Plugin plugin, String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if(!file.exists()) plugin.saveResource(fileName, false);
        Messages messages = of(YamlConfiguration.loadConfiguration(file), new MessageFactoryImpl(plugin));
        messages.verifier(new FileMessageVerifier(plugin.getResource(fileName), file));
        return messages;
    }

    /**
     * <h1>Create container using current plugin</h1>
     * Create container using messages.yml file by default from jar entry.
     * A new file in plugin folder will be created with content from messages.yml
     * <pre>
     * {@code
     * Messages messages = Messages.of(this);
     * }
     * </pre>
     * @param plugin - plugin that creates a container
     * @return container with messages
     */
    static Messages of(Plugin plugin) {
        return of(plugin, "messages.yml");
    }

    /**
     * <h1>Create container using file</h1>
     * <pre>
     * {@code
     * File file = new File("msg_file.yml");
     * Messages messages = Messages.of(file);
     * }
     * </pre>
     * @param file - file with messages
     * @return container with messages
     */
    static Messages of(File file, Plugin plugin) {
        return of(YamlConfiguration.loadConfiguration(file), new MessageFactoryImpl(plugin));
    }

    /**
     * <h1>Create container using path</h1>
     *
     * @param path - file with messages
     * @return container with messages
     * @see Messages#of(File, Plugin)
     */
    static Messages of(Path path, Plugin plugin) {
        return of(path.toFile(), plugin);
    }

    /**
     * Only legacy color codes. For versions lower than 1.18
     * @return container with messages
     */
    static Messages legacy(ConfigurationSection section, Plugin plugin) {
        return new MessagesImpl(section, new LegacyFactoryImpl(plugin));
    }

    /** @see Messages#legacy(ConfigurationSection, Plugin) */
    static Messages legacy(Plugin plugin, String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if(!file.exists()) plugin.saveResource(fileName, false);
        Messages messages = new MessagesImpl(file, new LegacyFactoryImpl(plugin));
        messages.verifier(new FileMessageVerifier(plugin.getResource(fileName), file));
        return messages;
    }

    /** @see Messages#legacy(ConfigurationSection, Plugin) */
    static Messages legacy(Plugin plugin) {
        return legacy(plugin, "messages.yml");
    }

    /** @see Messages#legacy(ConfigurationSection, Plugin) */
    static Messages legacy(File file, Plugin plugin) {
        return new MessagesImpl(file, new LegacyFactoryImpl(plugin));
    }

    /**
     * @return empty messages container
     * Use this with Reloader
     */
    static @NotNull Messages empty() {
        return new MessagesImpl();
    }

    /**
     * Singleton NULL_MESSAGE
     * @see NullMessage
     */
    Message NULL_MESSAGE = new NullMessage();
    @Override
    default int priority() {
        return 1;
    }
}
