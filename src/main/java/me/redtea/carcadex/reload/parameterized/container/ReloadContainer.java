package me.redtea.carcadex.reload.parameterized.container;

import me.redtea.carcadex.reload.parameterized.container.impl.MapReloadContainer;

import java.util.HashMap;
import java.util.Map;

public interface ReloadContainer {
    <T>T get(String key);
    <T>void set(String key, T t);
    boolean contains(String key);
    static ReloadContainer of(Object... objects) {
        Map<String, Object> map = new HashMap<>();
        for(int i = 0; i < objects.length; i += 2) {
            String key = (String) objects[i];
            Object value = objects[i+1];
            map.put(key, value);
        }
        return new MapReloadContainer(map);
    }
}
