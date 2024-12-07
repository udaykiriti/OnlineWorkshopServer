package com.studentworkshop.services;

import com.studentworkshop.models.User;
import com.studentworkshop.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TokenService {

    private final Map<String, User> tokenStore = new HashMap<>(); 

    @Autowired
    private UserRepository userRepository;

    public String generateTokenForUser(User user) {
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, user);
        return token;
    }

    public User validateTokenAndGetUser(String token) {
        return tokenStore.get(token);
    }

    public void invalidateToken(String token) {
        tokenStore.remove(token);
    }
}
