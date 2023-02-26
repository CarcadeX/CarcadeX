package me.redtea.carcadex.schema.impl.filesection;

import me.redtea.carcadex.schema.AbstractFileSchemaStrategy;
import me.redtea.carcadex.schema.impl.filesection.parser.SectionParser;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public abstract class FileSectionSchemaStrategy<K, V> extends AbstractFileSchemaStrategy<K, V> {
    private final SectionParser sectionParser;

    public FileSectionSchemaStrategy(Path dir, SectionParser sectionParser) {
        super(dir);
        this.sectionParser = sectionParser;
    }

    @Override
    public Collection<V> all() {
        if(dir.toFile().listFiles() == null) return new ArrayList<>();
        return Arrays.stream(dir.toFile().listFiles()).map(it -> fromSection(sectionParser.parse(it))).collect(Collectors.toList());
    }

    @Override
    public V get(K key) {
        return fromSection(sectionParser.parse(Arrays.stream(dir.toFile().listFiles()).filter(it -> it.getName().equals(String.valueOf(key)))
                .findFirst().get()));
    }

    @Override
    public void insert(K key, V value) {
        File file = Arrays.stream(dir.toFile().listFiles()).filter(it -> it.getName().equals(String.valueOf(key)))
                .findFirst().get();
        sectionParser.save(saveToSection(value, sectionParser.parse(file)), file);
    }

    abstract V fromSection(ConfigurationSection section);
    abstract ConfigurationSection saveToSection(V v, ConfigurationSection section);
}
