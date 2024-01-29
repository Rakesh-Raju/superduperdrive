package com.cloudstorage.services;
import com.cloudstorage.models.User;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private UserService userService;

    public AuthorizationService(UserService userService) {
        this.userService = userService;
    }

    public boolean registerUser(User user) {
        String username = user.getUsername();
        if (!userService.checkUsername(username)) {
            return false;
        }
        userService.register(user);
        return true;
    }
}