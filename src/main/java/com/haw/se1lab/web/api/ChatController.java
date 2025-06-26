package com.haw.se1lab.web.api;

import com.haw.se1lab.chatdata.logic.api.usecase.ChatUseCase;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatUseCase chatUseCase;

    public ChatController(ChatUseCase chatUseCase) {
        this.chatUseCase = chatUseCase;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllChatsByUser(@RequestParam String benutzerName) {
        if (benutzerName == null || benutzerName.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Benutzername must not be null or empty");
        }

        List<Chat> chats = chatUseCase.getAllChatsByUser(benutzerName);
        return ResponseEntity.ok(chats);
    }
}
