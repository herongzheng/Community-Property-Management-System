package com.laioffer.communitymanagement.fakeControllers;

import com.laioffer.communitymanagement.db.entity.Package;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class MessageController {

    public MessageController() {
    }

    /*
    * Once the resident logs in, this API should be called and the unread message number
    * appears on the top right icon
    * */
    @GetMapping("/unread")
    public int getUnreadCount(Principal principal) {
        return 3;
    }

    /*
    * This API is used by the resident to display all messages
    * Once the resident clicks on the message button in the top right corner, the screen will
    * display all the messages (about the packages). Most recent message is the top one
    * */
    @GetMapping("/message")
    public List<Package> displayPackageMessages(Principal principal) {
//        String username = principal.getName();
//        query to get all the package info from the package table with tenant_id = username
        Package pack1 = new Package().setDescription("this is the description of the package, TBA00123456").setRead(true);
        Package pack2 = new Package().setDescription("contains lithium batteries").setRead(true);
        return List.of(pack1, pack2);
    }
}
