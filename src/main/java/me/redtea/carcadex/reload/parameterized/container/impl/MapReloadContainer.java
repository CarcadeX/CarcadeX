package me.redtea.carcadex.reload.parameterized.container.impl;

import me.redtea.carcadex.reload.parameterized.container.ReloadContainer;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class MapReloadContainer implements ReloadContainer {
    private final Map<String, Object> params;
    @Override
    public <T> T get(String key) {
        return (T) params.get(key);
    }

    @Override
    public <T> void set(String key, T t) {
        params.put(key, t);
    }

    @Override
    public boolean contains(String key) {
        return params.containsKey(key);
    }
}
