package me.redtea.carcadex.chathandler;

import org.bukkit.entity.Player;

public interface ChatNotifiable {
    void notify(Player player, String message);
}
