package net.gark.mysports.services;

import net.gark.mysports.services.dto.DtoChatMessage;
import net.gark.mysports.services.interfaces.IServiceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceSocketMessagingTransmitterImplThread implements IServiceMessage {

    private final Logger LOGGER = LoggerFactory.getLogger(ServiceSocketMessagingTransmitterImplThread.class);
    private final int SECONDS_IN_A_DAY = 60 * 60 * 24;

    private final SimpMessagingTemplate webSocket;
    private final List<DtoChatMessage> messages = new ArrayList<>();
    private Thread t;
    private boolean DISABLED = true;

    @Autowired
    public ServiceSocketMessagingTransmitterImplThread(final SimpMessagingTemplate webSocket) {
        this.webSocket = webSocket;
    }

    @PostConstruct
    void init() {
        LOGGER.debug("Starting");
        t = new Thread(() -> {
            try {
                while (t != null) {
                    if (DISABLED) {
                        LOGGER.debug("Message Transmitter disabled");
                    } else {
                        pushChangesToWebSocket();
                    }
                    Thread.sleep(1000 * SECONDS_IN_A_DAY);
                }
            } catch (final InterruptedException ignored) {

            } finally {
                t = null;
            }
        });
        t.setDaemon(true);
        t.start();
    }

    @PreDestroy
    void destroy() throws InterruptedException {
        LOGGER.debug("Stopping");
        if (t != null) {
            final Thread t1 = t;
            t = null;
            t1.wait();
        }
    }

    @Override
    public List<DtoChatMessage> getMessages() {
        synchronized (messages) {
            return messages;
        }
    }

    @Override
    public void post(final DtoChatMessage msg) {
        synchronized (messages) {
            messages.add(msg);
        }
        webSocket.convertAndSend("/topic/messages", msg);
    }

    @Override
    @Async
    public void pushChangesToWebSocket() {

        LOGGER.debug("Pushing messages to topic");

        final List<DtoChatMessage> list = new ArrayList<>();
        final int count;
        synchronized (messages) {
            count = messages.size();
        }
        list.add(new DtoChatMessage("message " + (count + 1), "Server", OffsetDateTime.now()));

        for (final DtoChatMessage msg : list) {
            LOGGER.info("New message: {}", msg.message);
            webSocket.convertAndSend("/topic/messages", msg);
        }

        messages.addAll(list);
    }
}
