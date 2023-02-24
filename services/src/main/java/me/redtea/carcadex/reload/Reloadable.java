package me.redtea.carcadex.reload;

public interface Reloadable {
    void init();

    void close();

    default void reload() {
        close();
        init();
    }
}
