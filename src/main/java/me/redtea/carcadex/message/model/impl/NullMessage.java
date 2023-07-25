package me.redtea.carcadex.message.model.impl;

import com.cryptomorin.xseries.XMaterial;
import me.redtea.carcadex.message.model.Message;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class NullMessage implements Message {
    @Override
    public void send(@NotNull CommandSender sender) {

    }

    @Override
    public @NotNull Message replaceAll(@NotNull String from, @NotNull String to) {
        return this;
    }

    @Override
    public @NotNull List<Component> asComponentList() {
        return new ArrayList<>();
    }

    @Override
    public @NotNull List<String> asUnparsedStringList() {
        return new ArrayList<>();
    }

    @Override
    public @NotNull String asUnparsedString(@NotNull String newLine) {
        return "";
    }

    @Override
    public @NotNull XMaterial toXMaterial() {
        return XMaterial.AIR;
    }

    @Override
    public @NotNull Component asComponent() {
        return Component.text("");
    }
}
