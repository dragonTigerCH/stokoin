package com.bsc.stokoin.chat.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ChatController {


    @MessageMapping("/hello")
    @SendTo("/topic/greeting")
    public String chatGET(String data){
        log.info("@ChatController, chat GET()");
        log.info("data = {}",data);
        return data;
    }

}
