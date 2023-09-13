package com.laioffer.communitymanagement.packageAndMessage;

import com.laioffer.communitymanagement.db.entity.Package;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class MessageController {
    private final MessageService messageService;

    public MessageController (MessageService messageService) {
        this.messageService = messageService;
    }

    //    Use case: For each resident, show a list of messages by listing all unread messages first and then order them from oldest package to newest.
    @PostMapping("/messages")
    public List<Package> listMessages(Principal principal) {
        List<Package> allPackages = messageService.listByUsername(principal.getName());
        messageService.markMessageAsRead(principal.getName());
        return allPackages;
    }

    // Use case: Show each resident, how many unread messages/packages received so far.
    @GetMapping("/messages/unread_count")
    public int countUnreadMessages(Principal principal) {
        return messageService.checkAllUnreadMessages(principal.getName());
    }

}
