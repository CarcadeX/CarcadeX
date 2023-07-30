package me.redtea.carcadex.schema.file;

import lombok.SneakyThrows;
import me.redtea.carcadex.schema.SchemaStrategy;
import org.checkerframework.checker.nullness.Opt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public abstract class AbstractFileSchemaStrategy<K, V> implements SchemaStrategy<K, V> {
    protected Path folder;
    private String filenameExtension = "";

    public AbstractFileSchemaStrategy(File folder, String filenameExtension) {
        this(folder);
        if(!filenameExtension.contains(".") && !filenameExtension.equals("")) filenameExtension = "." + filenameExtension;
        this.filenameExtension = filenameExtension;
    }

    public AbstractFileSchemaStrategy(File folder) {
        this.folder = folder.toPath();
        init();
    }

    @Override
    public void init() {
        if(Files.notExists(folder)) {
            try {
                Files.createDirectory(folder);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected abstract V fromFile(String string);
    protected abstract String toFile(V value);

    @Override
    @SneakyThrows
    public Collection<V> all() {
        Collection<V> res;
        try (Stream<Path> walk = Files.walk(folder)) {
            res = walk.parallel().filter(Files::isRegularFile)
                    .map(it -> {
                        try {
                            return fromFile(String.join("\n", Files.readAllLines(it)));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
        }
        return res;
    }

    @Override
    public V get(K key) {
        try {
            Path file = folder.resolve(key + filenameExtension);
            if(Files.notExists(file)) throw new NoSuchElementException("No element found with key " + key);
            return fromFile(String.join("\n", Files.readAllLines(file)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(K key, V value) {
        Path file = folder.resolve(key + filenameExtension);
        try {
            if(Files.notExists(file)) Files.createFile(file);
            Files.write(file, toFile(value).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SneakyThrows
    public void remove(K key) {
       Files.deleteIfExists(folder.resolve(key + filenameExtension));
    }

    @Override
    public void close() {
    }

    @Override
    @SneakyThrows
    public Collection<V> find(Predicate<V> predicate) {
        Collection<V> res;
        try (Stream<Path> walk = Files.walk(folder)) {
            res = walk.parallel().filter(Files::isRegularFile)
                    .map(it -> {
                        try {
                            return fromFile(String.join("\n", Files.readAllLines(it)));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(predicate)
                    .collect(Collectors.toList());
        }
        return res;
    }

    @Override
    @SneakyThrows
    public Optional<V> findAny(Predicate<V> predicate) {
        Optional<V> res;
        try (Stream<Path> walk = Files.walk(folder)) {
            res = walk.parallel().filter(Files::isRegularFile)
                    .map(it -> {
                        try {
                            return fromFile(String.join("\n", Files.readAllLines(it)));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(predicate)
                    .findAny();
        }
        return res;
    }

    @SneakyThrows
    @Override
    public void removeAll() {
        Files.delete(folder);
    }
}

