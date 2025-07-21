package com.firsthelpfinancial.fhfwebsocket.component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class TopicConnectionManager implements ChannelInterceptor {

    private final Map<String, String> topicSessionMap = new ConcurrentHashMap<>();

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            String topic = accessor.getDestination();
            String sessionId = accessor.getSessionId();

            if (topicSessionMap.containsKey(topic)) {
                throw new IllegalStateException("Topic already being used.");
            }

            topicSessionMap.put(topic, sessionId);
        }

        if (StompCommand.UNSUBSCRIBE.equals(accessor.getCommand()) ||
                StompCommand.DISCONNECT.equals(accessor.getCommand())) {
            topicSessionMap.values().remove(accessor.getSessionId());
        }

        return message;
    }
}
