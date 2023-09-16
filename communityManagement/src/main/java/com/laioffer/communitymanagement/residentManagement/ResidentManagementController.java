package com.laioffer.communitymanagement.residentManagement;

import com.laioffer.communitymanagement.db.entity.User;
import com.laioffer.communitymanagement.residentManagement.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class ResidentManagementController {

    private final UserService userService;

    public ResidentManagementController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/manage/residents")
    public List<User> displayAllResidents() {
        return userService.findAllResidents();
    }

    @PostMapping("/manage/residents/new_resident/{username}")
    public void RenewResident(@PathVariable String username) {
        userService.deleteRecordAndResetUser(username);

    }
}