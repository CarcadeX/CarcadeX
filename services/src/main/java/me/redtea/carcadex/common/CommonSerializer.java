package me.redtea.carcadex.common;

public interface CommonSerializer<T> {
    String serialize(T t);
    T deserialize(String s);
}
