package me.redtea.carcadex.chathandler;

import me.redtea.carcadex.chathandler.impl.GlobalChatHandler;
import me.redtea.carcadex.reload.Reloadable;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface ChatHandler extends Reloadable {
    /**
     * Start the handle player
     * @param player player that we want to handle
     * @param chatAction functional interface. 
     * It will be executed when writing a player's message to the chat. After the execution, the player is no longer handled
     */
    void handle(@NotNull Player player, @NotNull ChatAction chatAction);

    /**
     * Stop handling player
     * @param player player that we want to stop handle
     */
    void unhandle(@NotNull Player player);

    /**
     * Binds ChatHandler to plugin and registers it's listener
     * @param plugin the plugin to which we are binding
     */
    void bind(@NotNull Plugin plugin);
    ChatHandler global = new GlobalChatHandler();

    /**
     * @return instance of the ChatHandler class
     */
    static ChatHandler getInstance() {
        return global;
    }
}
