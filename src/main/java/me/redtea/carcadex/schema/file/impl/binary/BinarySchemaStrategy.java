package me.redtea.carcadex.schema.file.impl.binary;

import lombok.SneakyThrows;
import me.redtea.carcadex.schema.file.AbstractFileSchemaStrategy;

import java.io.*;
import java.util.Base64;

public class BinarySchemaStrategy<K, V> extends AbstractFileSchemaStrategy<K, V> {

    public BinarySchemaStrategy(File folder) {
        super(folder);
    }

    @SneakyThrows
    @Override
    protected V fromFile(String string) {
        InputStream in = new ByteArrayInputStream(Base64.getDecoder().decode(string));
        ObjectInputStream objectInputStream = new ObjectInputStream(in);
        V result = (V) objectInputStream.readObject();
        objectInputStream.close();
        return result;
    }

    @SneakyThrows
    @Override
    protected String toFile(V value) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(baos);
        objectOutputStream.writeObject(value);
        objectOutputStream.flush();
        objectOutputStream.close();
        baos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

}
