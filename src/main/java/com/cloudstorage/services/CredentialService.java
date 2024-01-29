package com.cloudstorage.services;
import com.cloudstorage.mappers.CredentialMapper;
import com.cloudstorage.models.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class CredentialService {

    private EncryptionService encryptionService;
    private CredentialMapper credentialMapper;

    @Autowired
    public CredentialService(EncryptionService encryptionService, CredentialMapper credentialMapper) {
        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
    }

    public List<Credentials> getFromID(Integer userID) {
        List<Credentials> credentials = credentialMapper.findByUserID(userID);
        for(Credentials c: credentials) {
            decryptCredential(c);
        }
        return credentials;
    }

    public Credentials getCredential(Integer credentialID) {
        Credentials credential = credentialMapper.findOne(credentialID);
        decryptCredential(credential);
        return credential;
    }

    public void decryptCredential(Credentials credential) {
        String password = credential.getPassword();
        String key = credential.getKey();
        String decryptedPW = encryptionService.decryptValue(password, key);
        credential.setDecryptedPW(decryptedPW);
    }

    public Boolean insertOrUpdate(Credentials credential, Integer userID) {
        String password = credential.getPassword();
        Integer credentialID = credential.getCredentialId();
        SecureRandom random = new SecureRandom();
        byte[] encodedKey = new byte[16];
        random.nextBytes(encodedKey);
        String key = Base64.getEncoder().encodeToString(encodedKey);
        String securePass = encryptionService.encryptValue(password, key);
        Credentials creds = new Credentials(credentialID, userID, credential.getUrl(), credential.getUsername(), key, securePass);
        if (credentialID != null && credentialID > 0) {
            credentialMapper.update(creds, credentialID);
        } else {
            credentialMapper.insert(creds, userID);
        }
        return true;
    }

    public Boolean delete(int credentialID) {
        credentialMapper.delete(credentialID);
        return true;
    }
}