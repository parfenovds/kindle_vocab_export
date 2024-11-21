package com.parfenov.kindleVocabExport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDTO {
  private Long id;
  private String name;
  private String changedName;
  private Long userId;
}
