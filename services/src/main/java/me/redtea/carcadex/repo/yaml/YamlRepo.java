package me.redtea.carcadex.repo.yaml;

import me.redtea.carcadex.repo.map.MapRepo;
import me.redtea.carcadex.repo.yaml.strategy.ParseStrategy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.nio.file.Files;
import java.nio.file.Path;

public class YamlRepo<K, V> extends MapRepo<K, V> {
    protected final Path file;

    protected final Plugin plugin;

    protected final ParseStrategy parseStrategy;

    public YamlRepo(Path file, Plugin plugin, ParseStrategy parseStrategy) {
        this.file = file;
        this.plugin = plugin;
        this.parseStrategy = parseStrategy;
        init();
    }


    protected FileConfiguration initFile() {
        if (Files.notExists(file)) plugin.saveResource(file.toFile().getName(), false);
        return YamlConfiguration.loadConfiguration(file.toFile());
    }


    @Override
    public void init() {
        parseStrategy.fromYaml(initFile());
    }
}
