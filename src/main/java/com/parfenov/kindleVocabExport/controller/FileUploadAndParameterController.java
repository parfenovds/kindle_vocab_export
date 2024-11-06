package com.parfenov.kindleVocabExport.controller;

import com.parfenov.kindleVocabExport.entity.Card;
import com.parfenov.kindleVocabExport.entity.Lookup;
import com.parfenov.kindleVocabExport.filter.CardFilter;
import com.parfenov.kindleVocabExport.service.BasicService;
import com.parfenov.kindleVocabExport.service.CardService;
import com.parfenov.kindleVocabExport.service.CsvExportService;
import com.parfenov.kindleVocabExport.service.TempFileService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Set;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadAndParameterController {

  private final TempFileService tempFileService;
  private final CsvExportService csvExportService;
  private final CardService cardService;
  private final BasicService basicService;

  public FileUploadAndParameterController(
      TempFileService tempFileService, CsvExportService csvExportService, CardService cardService,
      BasicService basicService) {
    this.tempFileService = tempFileService;
    this.csvExportService = csvExportService;
    this.cardService = cardService;
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
