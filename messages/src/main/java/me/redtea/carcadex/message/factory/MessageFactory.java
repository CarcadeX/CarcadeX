package me.redtea.carcadex.message.factory;

import me.redtea.carcadex.message.factory.impl.MessageFactoryImpl;
import me.redtea.carcadex.message.model.Message;

import java.util.List;

import static me.redtea.carcadex.message.container.Messages.NULL_MESSAGE;

public interface MessageFactory {
    Message message(List<String> unparsed);
    Message message(String msg);
    Message legacyMessage(List<String> unparsed);
    Message legacyMessage(String msg);

    default Message nullMessage() {
        return NULL_MESSAGE;
    }

    MessageFactory instance = new MessageFactoryImpl();
}
