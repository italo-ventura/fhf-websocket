package com.firsthelpfinancial.fhfwebsocket.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firsthelpfinancial.fhfwebsocket.model.EventMessage;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@RestController
@RequestMapping("/api/app-events")
@Slf4j
@CrossOrigin(origins = "http://localhost:8081")
public class EventsController {

    private final SimpMessagingTemplate messagingTemplate;

    public EventsController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/{applicationId}")
    public void publishAppEvent(@RequestBody EventMessage message, @PathVariable String applicationId) {
        switch (message.getAction()) {
            case "application-updated":
                messagingTemplate.convertAndSend(String.format("/topic/%s/events", applicationId), Map.of("message", "This application was updated", "type", "event"));
                break;
            case "events-finished":
                messagingTemplate.convertAndSend(String.format("/topic/%s/events", applicationId), Map.of("message", "Events for application are finished", "type", "event"));
                break;
            default:
                messagingTemplate.convertAndSend(String.format("/topic/%s/events", applicationId), Map.of("message", "Another Event Happened", "type", "event"));
        }

    }
}
