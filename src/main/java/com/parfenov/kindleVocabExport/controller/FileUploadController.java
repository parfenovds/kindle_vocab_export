package com.parfenov.kindleVocabExport.controller;

import com.parfenov.kindleVocabExport.service.TempFileService;
import java.io.IOException;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

  private final TempFileService tempFileService;

  public FileUploadController(TempFileService tempFileService) {
    this.tempFileService = tempFileService;
  }

  @GetMapping("/")
  public String redirectToUploadForm() {
    return "redirect:/upload";
  }

  @GetMapping("/upload")
  public String showUploadForm() {
    return "upload";
  }

  @PostMapping("/upload")
  public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException {
    String userKey = UUID.randomUUID().toString();
    tempFileService.uploadDatabaseFile(file, userKey);
    model.addAttribute("userKey", userKey);
    return "redirect:/parameters?userKey=" + userKey;
  }
}
