package me.redtea.carcadex.message.model;

import com.cryptomorin.xseries.XMaterial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * <h1>Message</h1>
 * This class provides message management methods. You can also convert it to Component.
 * @since 1.0.0
 * @author itzRedTea
 */
public interface Message extends ComponentLike {
    /**
     * Send message to CommandSender
     * @param sender recipient of the message
     */
    void send(@NotNull CommandSender sender);

    /**
     * Replaces all matches in unparsed message
     * @param from what you want to replace
     * @param to what do you want to replace
     * @return copy of the Message with the changes applied to it
     */
    @NotNull
    Message replaceAll(@NotNull String from, @NotNull String to);

    /**
     * Convert message to list of components
     * @return list of components
     */
    @NotNull
    List<Component> asComponentList();
    /**
     * Get unparsed value of message
     */
    @NotNull
    List<String> asUnparsedStringList();
    /**
     * Get unparsed value of message joined to one string
     */
    @NotNull
    String asUnparsedString(@NotNull String newLine);
    /**
     * Get content of message
     */
    @NotNull
    String content();

    /**
     * Convert message to XMaterial
     * @return found material
     */
    @NotNull
    XMaterial toXMaterial();

    /**
     * @see Message#toXMaterial() 
     */
    default Material toMaterial() {
        return toXMaterial().parseMaterial();
    }

    /**
     * @see Message#asUnparsedString(String) ()
     */
    default String asUnparsedString() {
        return asUnparsedString("\n");
    }

    /**
     * @see Message#replaceAll(String, String)
     */
    @NotNull
    default Message replaceAll(@NotNull Object from, @NotNull Object to) {
        return replaceAll(String.valueOf(from), String.valueOf(to));
    }
}
