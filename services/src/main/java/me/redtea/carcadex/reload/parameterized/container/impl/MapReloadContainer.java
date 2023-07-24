package me.redtea.carcadex.reload.parameterized.container.impl;

import lombok.RequiredArgsConstructor;
import me.redtea.carcadex.reload.parameterized.container.ReloadContainer;

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
