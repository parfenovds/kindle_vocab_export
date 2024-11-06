package com.parfenov.kindleVocabExport.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardFilter {
  private MultipartFile file;
  private String dateFrom;
  private String dateTo;
  private String sourceLanguage;
  private String targetLanguage;
  private Integer limit;
  private String timestamp;
}
