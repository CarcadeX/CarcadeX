package me.redtea.carcadex.utils;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

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
    public static UUID uuidByName(@NotNull String name) {
        return Bukkit.getOfflinePlayer(name).getUniqueId();
    }
}
