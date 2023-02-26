package me.redtea.carcadex.schema.impl.binary;

import me.redtea.carcadex.schema.AbstractFileSchemaStrategy;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class BinarySchemaStrategy<K, V> extends AbstractFileSchemaStrategy<K, V> {

    public BinarySchemaStrategy(Path dir) {
        super(dir);
    }


    @Override
    public Collection<V> all() {
        if(dir.toFile().listFiles() != null)
        return Arrays.stream(dir.toFile().listFiles()).map(this::read).collect(Collectors.toList());
        else return new ArrayList<>();
    }

    @Override
    public V get(K key) {
        File file = new File(dir.toFile(), key+".bin");
        return read(file);
    }

    private V read(File file)  {
        try {
            if(!file.exists()) file.createNewFile();
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            V result = (V) objectInputStream.readObject();
            objectInputStream.close();
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(K key, V value) {
        try {
            File file = new File(dir.toFile(), key+".bin");
            if(!file.exists()) file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(value);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
