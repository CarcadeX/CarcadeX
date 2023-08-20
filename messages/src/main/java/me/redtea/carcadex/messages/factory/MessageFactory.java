package me.redtea.carcadex.messages.factory;

import me.redtea.carcadex.messages.container.Messages;
import me.redtea.carcadex.messages.model.Message;

import java.util.List;

public interface MessageFactory {
    Message message(List<String> unparsed);
    Message message(String msg);
    Message legacyMessage(List<String> unparsed);
    Message legacyMessage(String msg);

    default Message nullMessage() {
        return Messages.NULL_MESSAGE;
    }
}
