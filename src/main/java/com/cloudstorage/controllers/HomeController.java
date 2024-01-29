package com.cloudstorage.controllers;
import com.cloudstorage.models.User;
import com.cloudstorage.services.AuthorizationService;
import com.cloudstorage.services.CredentialService;
import com.cloudstorage.services.FileService;
import com.cloudstorage.services.NoteService;
import com.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;


@Controller
public class HomeController {

    @Autowired
    private AuthorizationService authService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;


    @GetMapping("/home")
    public String getHomePage(Authentication auth, Model model) throws Exception {
        User user = userService.getUser((String) auth.getPrincipal());
        if (user == null) {
            return "redirect:login";
        }
        model.addAttribute("notesList", noteService.getNotes(user.getUserID()));
        model.addAttribute("credentialsList", credentialService.getFromID(user.getUserID()));
        model.addAttribute("filesList", fileService.getFilesByID(user.getUserID()));
        return "home";
    }

    @GetMapping(value = {"/", "/login"})
    public String loginHandler(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("toLogin", true);
        return "login";
    }

    @PostMapping("/logout")
    public String logoutHandler(@ModelAttribute("user") User user, Model model) {
        return "redirect:login?logout=true";
    }

    @GetMapping("/signup")
    public String signUp(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("toSignup", true);
        return "signup";
    }

    @PostMapping("/signup")
    public String register(@ModelAttribute("user") User user, Model model) {
        if (!authService.registerUser(user)) {
            model.addAttribute("toSignup", true);
            return "redirect:signup?error";
        } else {
            model.addAttribute("toSignup", false);
            return "redirect:signup?success";
        }

    }

    @GetMapping("/result")
    public String result(@RequestParam(required = false, name = "isSuccess") Boolean isSuccess, Model model, Authentication auth) {
        User user = userService.getUser((String) auth.getPrincipal());
        if (user == null) {
            return "redirect:login";
        }
        model.addAttribute("isSuccess", isSuccess);
        return "result";
    }
}