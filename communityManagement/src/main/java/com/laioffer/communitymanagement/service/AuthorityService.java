package com.laioffer.communitymanagement.service;

import com.laioffer.communitymanagement.db.AuthorityRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {
    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public String findAuthorityByUsername(String username) {
        return authorityRepository.findAuthorityByUsername(username).getAuthority();
    }
}
