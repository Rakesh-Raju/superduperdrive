package com.cloudstorage.controllers;
import com.cloudstorage.models.File;
import com.cloudstorage.services.FileService;
import com.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import java.io.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;


    @PostMapping("")
    public String upload(@RequestParam("fileUpload") MultipartFile file, Authentication authentication) throws IOException {
        if(file == null) {
            return "redirect:/result?isSuccess=false";
        }
        String username = (String) authentication.getPrincipal();

        if(file.isEmpty()) {
            return "redirect:/result?isSuccess=false";
        }

        boolean res = fileService.insert(file, userService.getUser(username).getUserID());
        return "redirect:/result?isSuccess=" + res;

    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "fileId") Integer fileID) {
        if(fileID == null) {
            return "redirect:/result?isSuccess=false";
        }
        return "redirect:/result?isSuccess=" + fileService.delete(fileID);
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> download(@RequestParam(name = "fileId") Integer fileId) {
        File file = fileService.getFile(fileId);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(file.getFileData()));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFileName()).contentType(MediaType.parseMediaType(file.getContentType())).body(res);
    }

}

