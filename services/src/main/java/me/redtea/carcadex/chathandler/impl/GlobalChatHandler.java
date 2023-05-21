package me.redtea.carcadex.chathandler.impl;

import com.google.common.collect.Lists;
import me.redtea.carcadex.chathandler.ChatAction;
import me.redtea.carcadex.chathandler.ChatHandler;
import me.redtea.carcadex.chathandler.ChatNotifiable;
import me.redtea.carcadex.chathandler.handler.XCarcadeChatHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalChatHandler implements ChatNotifiable, ChatHandler {
    private final Map<String, List<ChatAction>> registered = new HashMap<>();
    private Plugin plugin = null;
    @Override
    public void handle(Player player, ChatAction chatAction) {
        if(!registered.containsKey(player.getName())) registered.put(player.getName(), Lists.newArrayList(chatAction));
        else registered.get(player.getName()).add(chatAction);
    }

    @Override
    public void unhandle(Player player) {
        registered.remove(player.getName());
    }

    @Override
    public void bind(Plugin plugin) {
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
}
