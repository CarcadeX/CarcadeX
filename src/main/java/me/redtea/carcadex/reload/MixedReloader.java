package me.redtea.carcadex.reload;

import me.redtea.carcadex.reload.exception.SimpledReloaderException;
import me.redtea.carcadex.reload.parameterized.ParameterizedReloadable;
import me.redtea.carcadex.reload.parameterized.container.ReloadContainer;
import com.google.inject.Injector;

import java.util.*;

public class MixedReloader implements Reloader {
    private final Injector injector;

    private final Set<Reloadable> reloadables = new HashSet<>();

    private final TimerTask reloadTask = new TimerTask() {
        @Override
        public void run() {
            reload();
        }
    };
    private final Map<Class<?>, ReloadContainer> containers = new HashMap<>();

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
        for(Reloadable reloadable : reloadables) {
            if(reloadable instanceof ParameterizedReloadable) {
                ((ParameterizedReloadable) reloadable).init(containers.get(reloadable.getClass()));
            } else reloadable.reload();
        }
        return new Date().getTime() - time;
    }

    @Override
    public void close() {
        reloadables.forEach(Reloadable::close);
    }

    @Override
    public <T extends ParameterizedReloadable> void container(Class<T> tClass, ReloadContainer container) {
        containers.put(tClass, container);
    }

    @Override
    public void autoSaveInterval(long interval) {
        reloadTask.cancel();
        timer.cancel();
        timer.scheduleAtFixedRate(reloadTask, 0, interval);
    }
}
