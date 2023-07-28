package me.redtea.carcadex.repo.impl.yaml;

import me.redtea.carcadex.repo.impl.map.MapRepo;
import me.redtea.carcadex.repo.impl.yaml.strategy.ParseStrategy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

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


    @Override
    public void init() {
        data.putAll(parseStrategy.fromYaml(initFile()));
    }
}
