package com.parfenov.kindleVocabExport.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class BookInfoDTO {
  String author;
  String title;
}