package me.redtea.carcadex.schema.impl.filesection.parser.impl;

import me.redtea.carcadex.schema.impl.filesection.parser.SectionParser;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlSectionParser implements SectionParser {
    @Override
    public ConfigurationSection parse(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public void save(ConfigurationSection section, File file) {
        try {
            ((FileConfiguration) section).save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
