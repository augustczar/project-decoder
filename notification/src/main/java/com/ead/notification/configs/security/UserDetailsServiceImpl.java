package com.ead.notification.configs.security;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl{

    public UserDetailsImpl build(UUID userId, String rolesStr) {
        return UserDetailsImpl.build(userId, rolesStr);
    }
}