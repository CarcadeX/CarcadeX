package me.redtea.carcadex.chathandler;

import org.bukkit.entity.Player;

public interface ChatAction {
    void onChat(Player player, String message);
}
