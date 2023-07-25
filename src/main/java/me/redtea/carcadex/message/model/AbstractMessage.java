package me.redtea.carcadex.message.model;

import com.cryptomorin.xseries.XMaterial;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMessage implements Message {
    protected final List<String> unparsed;

    protected List<Component> parsed = null;

    protected final BukkitAudiences adventure;

    public AbstractMessage(List<String> unparsed, Plugin plugin) {
        this.unparsed = unparsed;
        adventure = BukkitAudiences.create(plugin);
    }

    protected abstract Component parse(String unparsed);

    private void parse() {
        parsed = new ArrayList<>();
        for(String s : unparsed) {
            parsed.add(parse(s));
        }
    }

    @Override
    public void send(@NotNull CommandSender sender) {
        parse();
        Audience audience = adventure.sender(sender);
        parsed.forEach(audience::sendMessage);
    }

    private Component cacheComponent = null;

    @Override
    @NotNull
    public List<Component> asComponentList() {
        parse();
        return parsed;
    }

    @Override
    public @NotNull Component asComponent() {
        if(cacheComponent == null) {
            parse();
            Component first = parsed.get(0);
            for(int i = 1; i < parsed.size(); i++) {
                first = first.append(parsed.get(i)).append(Component.newline());
            }
            cacheComponent = first;
        }
        return cacheComponent;
    }

    @Override
    @NotNull
    public List<String> asUnparsedStringList() {
        return unparsed;
    }

    private String cacheString = null;
    @Override
    @NotNull
    public String asUnparsedString(@NotNull String newLine) {
        if(cacheString == null) cacheString = String.join(newLine, unparsed);
        return cacheString;
    }

    @Override
    @NotNull
    public XMaterial toXMaterial() {
        return XMaterial.valueOf(asUnparsedString(""));
    }
}
