package com.pwr.project.controllers;

import com.pwr.project.dto.MessageDTO;
import com.pwr.project.services.ChatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/messages/{offerId}")
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable Long offerId, @RequestParam String userId) {
        List<MessageDTO> messages = chatService.getMessages(offerId, userId);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/messages/{offerId}")
    public ResponseEntity<Void> sendMessage(@PathVariable Long offerId, @RequestBody MessageDTO messageDTO) {
        chatService.sendMessage(offerId, messageDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user-messages/{userId}")
    public ResponseEntity<List<MessageDTO>> getUserMessages(@PathVariable String userId) {
        List<MessageDTO> messages = chatService.getUserMessages(userId);
        return ResponseEntity.ok(messages);
    }
}
