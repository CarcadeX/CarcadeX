package me.redtea.carcadex.data.bukkitextensions.yaml;

import me.redtea.carcadex.data.bukkitextensions.yaml.strategy.ParseStrategy;
import me.redtea.carcadex.data.repo.impl.map.MapRepo;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class YamlRepo<K, V> extends MapRepo<K, V> {
    protected final File file;

    protected final Plugin plugin;

    protected final ParseStrategy<K, V> parseStrategy;

    public YamlRepo(File file, Plugin plugin, ParseStrategy<K, V> parseStrategy) {
        this.file = file;
        this.plugin = plugin;
        this.parseStrategy = parseStrategy;
        init();
    }


    protected FileConfiguration initFile() {
        if (!file.exists()) plugin.saveResource(file.getName(), false);
        return YamlConfiguration.loadConfiguration(file);
    }

    public void init() {
        data.putAll(parseStrategy.fromYaml(initFile()));
    }
}
