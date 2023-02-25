package me.redtea.carcadex.message.model;

import com.cryptomorin.xseries.XMaterial;
import me.redtea.carcadex.message.model.impl.ComponentMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractMessage implements Message {
    protected final List<String> unparsed;

    protected List<Component> parsed = null;

    public AbstractMessage(List<String> unparsed) {
        this.unparsed = unparsed;
    }

    protected abstract void parse();

    @Override
    public void send(@NotNull CommandSender sender) {
        parse();
        parsed.forEach(sender::sendMessage);
    }

    private Component cacheComponent = null;

    @Override
    @NotNull
    public List<Component> asComponentList() {
        parse();
        return parsed;
    }

    @Override
    @NotNull
    public Message replaceAll(@NotNull String from, @NotNull String to) {
        return new ComponentMessage(unparsed.stream().map(it -> it.replaceAll(from, to)).collect(Collectors.toList()));
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
    public String content() {
        return PlainTextComponentSerializer.plainText().serialize(asComponent());
    }

    @Override
    @NotNull
    public XMaterial toXMaterial() {
        return XMaterial.valueOf(content());
    }
}
