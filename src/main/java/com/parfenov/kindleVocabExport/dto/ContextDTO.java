package com.parfenov.kindleVocabExport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContextDTO {
  private Long id;
  private String originalSentence;
  private String translatedSentence;
  private Long bookId;
}