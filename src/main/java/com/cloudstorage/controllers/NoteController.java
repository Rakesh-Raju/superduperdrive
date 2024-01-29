package com.cloudstorage.controllers;
import com.cloudstorage.models.Notes;
import com.cloudstorage.services.NoteService;
import com.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;

    @PostMapping("")
    public String submit(@ModelAttribute("note") Notes note, Authentication auth) {
        Integer userID = userService.getUser((String) auth.getPrincipal()).getUserID();
        return "redirect:/result?isSuccess=" + noteService.insertOrUpdate(note, userID);
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = false, name = "noteID") Integer noteID) {
        return "redirect:/result?isSuccess=" + noteService.delete(noteID);
    }
}
