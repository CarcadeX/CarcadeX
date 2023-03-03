package me.redtea.carcadex.repo.impl.yaml.strategy;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;

public interface ParseStrategy<K, V> {
    Map<K, V> fromYaml(FileConfiguration fileConfiguration);
}
