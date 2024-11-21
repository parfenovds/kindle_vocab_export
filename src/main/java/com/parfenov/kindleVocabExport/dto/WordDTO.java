package com.parfenov.kindleVocabExport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordDTO {
  private Long id;
  private String word;
  private String language;
  private Long userId;
}
