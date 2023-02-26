package me.redtea.carcadex.schema.impl.filesection.parser;

import org.bukkit.configuration.ConfigurationSection;

import java.io.File;

public interface SectionParser {
    ConfigurationSection parse(File file);
    void save(ConfigurationSection section, File file);
}
