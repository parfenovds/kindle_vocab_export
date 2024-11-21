package com.parfenov.kindleVocabExport.controller;

import com.parfenov.kindleVocabExport.service.DatabaseSavingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/database")
@RequiredArgsConstructor
public class DatabaseUploadController {
  private final DatabaseSavingService databaseSavingService;

  @GetMapping("/")
  public String showUploadForm(Model model) {
    return "uploadToDatabase";
  }

  @PostMapping("/upload-to-db")
  public ResponseEntity<String> uploadVocabToDatabase(@RequestParam("file") MultipartFile file) {
    try {
      databaseSavingService.saveToDatabase(file);
      return ResponseEntity.ok("File uploaded and processed successfully!");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An error occurred: " + e.getMessage());
    }
  }
}