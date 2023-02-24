package me.redtea.carcadex.util;

import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * @since 1.0.0
 * @author itzRedTea
 */
public class PlayerUtils {
    /**
     * @param name nickname of player
     * @return uniqueId of this player
     */
    public static UUID uuidByName(String name) {
        return Bukkit.getOfflinePlayer(name).getUniqueId();
    }
}
