package me.redtea.carcadex.message.container.util;

import me.redtea.carcadex.utils.Provider;
import org.bukkit.configuration.ConfigurationSection;

public interface SectionProvider extends Provider<ConfigurationSection> {
    ConfigurationSection get();
}
