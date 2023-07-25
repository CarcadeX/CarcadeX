package me.redtea.carcadex.chathandler.handler;

import me.redtea.carcadex.chathandler.ChatNotifiable;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@RequiredArgsConstructor
public class XCarcadeChatHandler implements Listener {
    private final ChatNotifiable chatNotifiable;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        chatNotifiable.notify(event.getPlayer(), event.getMessage());
    }
}
