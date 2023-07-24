package me.redtea.carcadex.chathandler.impl;

import com.google.common.collect.Lists;
import me.redtea.carcadex.chathandler.ChatAction;
import me.redtea.carcadex.chathandler.ChatHandler;
import me.redtea.carcadex.chathandler.ChatNotifiable;
import me.redtea.carcadex.chathandler.handler.XCarcadeChatHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalChatHandler implements ChatNotifiable, ChatHandler {
    private Map<String, List<ChatAction>> registered;
    private Plugin plugin = null;

    @Override
    public void handle(@NotNull Player player, @NotNull ChatAction chatAction) {
        if(!registered.containsKey(player.getName())) registered.put(player.getName(), Lists.newArrayList(chatAction));
        else registered.get(player.getName()).add(chatAction);
    }

    @Override
    public void unhandle(@NotNull Player player) {
        registered.remove(player.getName());
    }

    @Override
    public void bind(@NotNull Plugin plugin) {
        if(this.plugin == null) {
            this.plugin = plugin;
            Bukkit.getPluginManager().registerEvents(new XCarcadeChatHandler(this), plugin);
        }
    }

    @Override
    public void notify(Player player, String message) {
        if(registered.containsKey(player.getName())) {
            registered.get(player.getName()).forEach((a) -> a.onChat(player, message));
            unhandle(player);
        }
    }

    @Override
    public void init() {
        if(registered == null) registered = new HashMap<>();
    }

    @Override
    public void close() {
        registered.clear();
    }
}
