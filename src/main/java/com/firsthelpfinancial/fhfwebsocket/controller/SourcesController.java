package com.firsthelpfinancial.fhfwebsocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.firsthelpfinancial.fhfwebsocket.model.SourceMessage;
import com.firsthelpfinancial.fhfwebsocket.model.Sources;

@Controller
public class SourcesController {


    @MessageMapping("/source-call-success")
    @SendTo("/topic/events")
    public SourceMessage successCall(Sources sources) throws Exception {
        Thread.sleep(5000); // simulated delay
        return new SourceMessage("Called " + HtmlUtils.htmlEscape(sources.getName()) + " module successfully!");
    }

    @MessageMapping("/source-call-error")
    @SendTo("/topic/sources")
    public SourceMessage errorCall(Sources sources) {
        return new SourceMessage("Error calling " + HtmlUtils.htmlEscape(sources.getName()) + " module!");
    }

}
