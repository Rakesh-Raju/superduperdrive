package com.cloudstorage.controllers;
import com.cloudstorage.models.Credentials;
import com.cloudstorage.services.CredentialService;
import com.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;
    @Autowired
    private UserService userService;

    @PostMapping("")
    public String submit(@ModelAttribute("credential") Credentials credential, Authentication authentication) {
        String username = (String) authentication.getPrincipal();
        return "redirect:/result?isSuccess=" + credentialService.insertOrUpdate(credential, userService.getUser(username).getUserID());
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = false, name = "credentialId") Integer credentialID) {
        return "redirect:/result?isSuccess=" + credentialService.delete(credentialID);
    }
}
