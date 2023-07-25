package me.redtea.carcadex.reload.parameterized;

import me.redtea.carcadex.reload.Reloadable;
import me.redtea.carcadex.reload.parameterized.container.ReloadContainer;
import org.apache.commons.lang.NotImplementedException;
import org.jetbrains.annotations.Nullable;

public abstract class ParameterizedReloadable implements Reloadable {
    public abstract void init(@Nullable ReloadContainer container);
    @Override
    public void init() {
        throw new NotImplementedException("This class can be initialized only with init(ReloadContainer) method");
    }

}
