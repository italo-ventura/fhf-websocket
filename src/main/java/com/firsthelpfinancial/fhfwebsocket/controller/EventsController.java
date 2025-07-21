package com.firsthelpfinancial.fhfwebsocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firsthelpfinancial.fhfwebsocket.model.EventMessage;

import lombok.extern.slf4j.Slf4j;

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
    public void publishAppEventREST(@RequestBody EventMessage message, @PathVariable String applicationId) {
        messagingTemplate.convertAndSend(String.format("/topic/%s/events", applicationId), message);
    }

    @MessageMapping("/{applicationId}")
    @SendTo("/topic/{applicationId}/events")
    public EventMessage publishAppEventWS(EventMessage message) {
        return message;
    }
}
