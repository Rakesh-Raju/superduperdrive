package com.cloudstorage.models;

public class Credentials {
    private Integer credentialId;
    private Integer userId;
    private String url;
    private String username;
    private String key;
    private String password;
    private String decryptedPW;

    public Credentials(Integer credentialId, Integer userId, String url, String username, String key, String password) {
        this.credentialId = credentialId;
        this.userId = userId;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
    }

    public Credentials(){}

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDecryptedPW() {
        return this.decryptedPW;
    }

    public void setDecryptedPW(String decryptedPW) {
        this.decryptedPW = decryptedPW;
    }

}
