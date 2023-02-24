package me.redtea.carcadex.message.container;

import me.redtea.carcadex.message.container.impl.MessagesImpl;
import me.redtea.carcadex.message.model.Message;
import me.redtea.carcadex.message.model.impl.NullMessage;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

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
 * @since 1.0.0
 * @author itzRedTea
 */
public interface Messages {
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
    Message get(String key);

    /**
     * <h1>Put message to container</h1>
     * @param key key/yaml path to message
     * @param message message to mut
     * @return putted message
     * @see Message
     */
    Message put(String key, Message message);

    /**
     * @param key key/yaml path to message
     * @return is there a message with such a key in the container?
     */
    boolean has(String key);

    /**
     * <h1>Reload container with messages in section from args.</h1>
     * @param section - ConfigurationSection with messages
     */
    void reload(ConfigurationSection section);

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
    static Messages of(ConfigurationSection section) {
        return new MessagesImpl(section);
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
        return of(YamlConfiguration.loadConfiguration(file));
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
     * Singleton NULL_MESSAGE
     * @see NullMessage
     */
    Message NULL_MESSAGE = new NullMessage();
}
