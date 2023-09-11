package com.laioffer.communitymanagement.residentManagement;


import com.laioffer.communitymanagement.chat.ChatService;
import com.laioffer.communitymanagement.db.PackageRepository;
import com.laioffer.communitymanagement.db.PostRepository;
import com.laioffer.communitymanagement.db.entity.IssueRepository;
import com.laioffer.communitymanagement.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.laioffer.communitymanagement.db.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PackageRepository packageRepository;
    private final IssueRepository issueRepository;
    private final ChatService chatService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PostRepository postRepository,
                       PackageRepository packageRepository,
                       IssueRepository issueRepository,
                       ChatService chatService,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.packageRepository = packageRepository;
        this.issueRepository = issueRepository;
        this.chatService = chatService;
        this.passwordEncoder = passwordEncoder;
    }


    public List<User> findAllResidents() {
        return userRepository.selectAllResidentsOrdered();
    }

    @Transactional
    public void deleteRecordAndResetUser(String username) {
        // delete the associated posts, likes, replies and like of replies
        List<Long> postIds = postRepository.findPostIdByUsername(username);
        postIds.stream().parallel().forEach(chatService::deleteAPost);

        // delete the associated packages
        List<Long> packageIds = packageRepository.findPackageIdByUsername(username);
        packageRepository.deleteAllById(packageIds);

        // delete the associated issues and issue images
        List<Long> issueIds = issueRepository.findIssueIdByUsername(username);
        issueRepository.deleteAllById(issueIds);

        // update the reset the user information and generate the temporary password
        String newPassword = passwordEncoder.encode("123456");
        userRepository.updateUserInfo(username, newPassword, null, null, null, null);
    }
}