package me.redtea.carcadex.repo;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.nio.file.Files;
import java.nio.file.Path;

public abstract class YamlRepo<K, V> extends MapRepo<K, V> {
    protected final Path file;

    protected final Plugin plugin;

    protected YamlRepo(Path file, Plugin plugin) {
        this.file = file;
        this.plugin = plugin;
        deserializeFromYaml(initFile());
    }

    abstract void deserializeFromYaml(FileConfiguration fileConfiguration);

    protected FileConfiguration initFile() {
        if (Files.notExists(file)) plugin.saveResource(file.toFile().getName(), false);
        return YamlConfiguration.loadConfiguration(file.toFile());
    }


}
