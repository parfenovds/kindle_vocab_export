package com.parfenov.kindleVocabExport.controller;

import com.parfenov.kindleVocabExport.filter.CardFilter;
import com.parfenov.kindleVocabExport.service.BasicService;
import java.io.ByteArrayInputStream;
import java.util.UUID;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FileUploadAndParameterController {
  private final BasicService basicService;

  public FileUploadAndParameterController(BasicService basicService) {
    this.basicService = basicService;
  }

  @GetMapping("/")
  public String showUploadAndParameterForm(Model model) {
    return "uploadAndParameters";
  }

  @PostMapping("/process")
  public ResponseEntity<InputStreamResource> handleFileUploadAndGenerateCsv(@ModelAttribute CardFilter filter) {
    String userKey = UUID.randomUUID().toString();
    ByteArrayInputStream csvContent = basicService.proceed(filter, userKey);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "attachment; filename=output.csv");
    return ResponseEntity.ok()
        .headers(headers)
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(new InputStreamResource(csvContent));
  }
}
