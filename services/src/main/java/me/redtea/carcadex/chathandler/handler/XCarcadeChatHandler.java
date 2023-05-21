package me.redtea.carcadex.chathandler.handler;

import lombok.RequiredArgsConstructor;
import me.redtea.carcadex.chathandler.ChatNotifiable;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@RequiredArgsConstructor
public class XCarcadeChatHandler implements Listener {
    private final ChatNotifiable chatNotifiable;

    public void onChat(AsyncPlayerChatEvent event) {
        chatNotifiable.notify(event.getPlayer(), event.getMessage());
    }
}
