package com.parfenov.kindleVocabExport.entity.basic;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Lookup {
  private String id;
  private String wordKey;
  private String bookKey;
  private String dictKey;
  private String pos;
  private String usage;
  private Timestamp timestamp;
}
