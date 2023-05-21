package me.redtea.carcadex.chathandler;

import me.redtea.carcadex.chathandler.impl.GlobalChatHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public interface ChatHandler {
    void handle(Player player, ChatAction chatAction);
    void unhandle(Player player);
    void bind(Plugin plugin);
    ChatHandler global = new GlobalChatHandler();
    static ChatHandler getInstance() {
        return global;
    }
}
