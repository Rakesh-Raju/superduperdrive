package com.cloudstorage.services;
import com.cloudstorage.mappers.CredentialMapper;
import com.cloudstorage.mappers.UserMapper;
import com.cloudstorage.models.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {

    private CredentialMapper credentialMapper;
    private UserMapper userMapper;
    private HashService hashService;

    public AuthenticationService(CredentialMapper credentialMapper, UserMapper userMapper, HashService hashService) {
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {

        String username = auth.getName();
        String password = auth.getCredentials().toString();

        User user = userMapper.getUser(username);

        if (user != null) {
            String encodedSalt = user.getSalt();
            String hashedPassword = hashService.getHashedValue(password, encodedSalt);

            if (user.getPassword().equals(hashedPassword)) {
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}