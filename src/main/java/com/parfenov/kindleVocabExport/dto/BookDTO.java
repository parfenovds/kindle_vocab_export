package com.parfenov.kindleVocabExport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {
  private Long id;
  private String sourceDbId;
  private String title;
  private String changedTitle;
  private String language;
  private Long authorId;
}
