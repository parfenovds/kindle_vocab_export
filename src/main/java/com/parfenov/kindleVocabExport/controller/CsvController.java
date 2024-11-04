package com.parfenov.kindleVocabExport.controller;

import com.parfenov.kindleVocabExport.entity.Card;
import com.parfenov.kindleVocabExport.service.CardService;
import com.parfenov.kindleVocabExport.service.CsvExportService;
import java.io.ByteArrayInputStream;
import java.util.Set;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CsvController {

  private final CardService cardService;
  private final CsvExportService csvExportService;

  public CsvController(CardService cardService, CsvExportService csvExportService) {
    this.cardService = cardService;
    this.csvExportService = csvExportService;
  }

  @PostMapping("/process")
  public ResponseEntity<InputStreamResource> generateCsv(
      @RequestParam String dateFrom,
      @RequestParam String dateTo,
      @RequestParam String sourceLanguage,
      @RequestParam String targetLanguage,
      @RequestParam Integer limit,
      @RequestParam(required = false) String timestamp,
      @RequestParam String userKey) {

    Set<Card> processedCards = cardService.processCards(dateFrom, dateTo, sourceLanguage, targetLanguage, limit, timestamp, userKey);
    ByteArrayInputStream csvContent = csvExportService.exportToCsv(processedCards);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "attachment; filename=output.csv");

    return ResponseEntity.ok()
        .headers(headers)
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(new InputStreamResource(csvContent));
  }
}
