package me.redtea.carcadex.message.verifier.impl;

import lombok.SneakyThrows;
import me.redtea.carcadex.message.verifier.MessageVerifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

public class FileMessageVerifier implements MessageVerifier {
    private final ConfigurationSection defaultSection;

    private final FileConfiguration toSaveSection;
    private final File toSaveFile;

    @SneakyThrows
    public FileMessageVerifier(InputStream defaultResource, File toSaveFile) {
        defaultSection = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultResource, "UTF-8"));
        this.toSaveSection = YamlConfiguration.loadConfiguration(toSaveFile);
        this.toSaveFile = toSaveFile;
    }

    @Override
    public Optional<String> fromDefault(String key) {
        Optional<String> o = Optional.ofNullable(defaultSection.getString(key));
        o.ifPresent(s -> saveToMain(key, s));
        return o;
    }

    @SneakyThrows
    public void saveToMain(String path, Object obj) {
        toSaveSection.set(path, obj);
        toSaveSection.save(toSaveFile);
    }
}
