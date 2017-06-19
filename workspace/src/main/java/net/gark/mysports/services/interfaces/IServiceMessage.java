package net.gark.mysports.services.interfaces;

import net.gark.mysports.services.dto.DtoChatMessage;

import java.util.List;

public interface IServiceMessage {
    void pushChangesToWebSocket();

    List<DtoChatMessage> getMessages();

    void post(DtoChatMessage msg);
}
