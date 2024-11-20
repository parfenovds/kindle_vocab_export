package com.parfenov.kindleVocabExport.entity.basic;

import java.sql.Timestamp;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(of = "originalSentence")
public class Card {
  private Set<String> words;
  private String originalSentence;
  private String translatedSentence;
  private Timestamp timestamp;
  private String sourceLanguage;
  private String targetLanguage;
}
