package me.redtea.carcadex.data.serializer;

public interface CommonSerializer<T> {
    String serialize(T t);
    T deserialize(String s);
}
