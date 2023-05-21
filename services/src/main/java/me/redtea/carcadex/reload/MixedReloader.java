package me.redtea.carcadex.reload;

import com.google.inject.Injector;
import me.redtea.carcadex.reload.exception.SimpledReloaderException;

import java.util.*;

public class MixedReloader implements Reloader {
    private Injector injector;

    private final Set<Reloadable> reloadables = new HashSet<>();

    private final TimerTask reloadTask = new TimerTask() {
        @Override
        public void run() {
            reload();
        }
    };

    private final Timer timer = new Timer();

     MixedReloader() {
        injector = null;
    }

    public MixedReloader(Injector injector) {
        this.injector = injector;
    }

    @Override
    public void add(Reloadable... reloadables) {
        this.reloadables.addAll(Arrays.asList(reloadables));
    }

    @Override
    public <T extends Reloadable> void add(Class<T> type) {
         if(injector == null) throw new SimpledReloaderException();
         this.reloadables.add(injector.getInstance(type));
    }

    @Override
    public long reload() {
        long time = new Date().getTime();
        reloadables.forEach(Reloadable::reload);
        return new Date().getTime() - time;
    }

    @Override
    public void close() {
        reloadables.forEach(Reloadable::close);
    }

    @Override
    public void autoSaveInterval(long interval) {
        reloadTask.cancel();
        timer.cancel();
        timer.scheduleAtFixedRate(reloadTask, 0, interval);
    }
}
