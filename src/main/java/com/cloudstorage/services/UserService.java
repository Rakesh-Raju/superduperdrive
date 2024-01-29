package com.cloudstorage.services;
import com.cloudstorage.mappers.UserMapper;
import com.cloudstorage.models.User;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean checkUsername(String username) {
        return userMapper.getUser(username) == null;
    }

    public int register(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        User newUser = new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName());
        return userMapper.insert(newUser);
    }

    public User getUser(String username) {
        return userMapper.getUser(username);
    }
}
