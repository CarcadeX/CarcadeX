package me.redtea.carcadex.reload;

import me.redtea.carcadex.reload.parameterized.ParameterizedReloadable;
import me.redtea.carcadex.reload.parameterized.container.ReloadContainer;
import com.google.inject.Injector;

public interface Reloader {
    void add(Reloadable... reloadables);


    <T extends Reloadable> void add(Class<T> type);

    /**
     * @return time of reload
     */
    long reload();

    void close();
    <T extends ParameterizedReloadable>void container(Class<T> type, ReloadContainer container);

    void autoSaveInterval(long interval);

    static Reloader get(Injector injector, long autoSaveTime) {
        Reloader reloader = new MixedReloader(injector);
        reloader.autoSaveInterval(autoSaveTime);
        return reloader;
    }

    static Reloader get(Injector injector) {
        return new MixedReloader(injector);
    }

    static Reloader simpled() {
        return new MixedReloader();
    }
}
