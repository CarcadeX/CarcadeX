package me.redtea.carcadex.repo.yaml.strategy;

import org.bukkit.configuration.file.FileConfiguration;

public interface ParseStrategy {
    void fromYaml(FileConfiguration fileConfiguration);
}
