package com.parfenov.kindleVocabExport.service;

import com.parfenov.kindleVocabExport.constant.DateOption;
import com.parfenov.kindleVocabExport.entity.basic.Lookup;
import com.parfenov.kindleVocabExport.repository.LookupRepository;
import com.parfenov.kindleVocabExport.util.Converter;
import java.sql.Timestamp;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class LookupService {

  private final LookupRepository lookupRepository;

  public LookupService(LookupRepository lookupRepository) {
    this.lookupRepository = lookupRepository;
  }

  public Set<Lookup> getFiltered(String dbFilePath, String dateFrom, String dateTo, String sourceLanguage, Integer limit, String timestamp) {
    Timestamp timestampFrom = getStartingTimestamp(dateFrom, timestamp, dbFilePath);
    if (dateTo == null || dateTo.isEmpty()) {
      dateTo = getDateForLimit(DateOption.MAX, dbFilePath);
    }
    if (limit == null) {
      limit = Integer.MAX_VALUE;
    }
    return lookupRepository.getFiltered(
        dbFilePath,
        timestampFrom,
        Converter.convertStringToTimestamp(dateTo, false),
        sourceLanguage,
        limit
    );
  }

  private Timestamp getStartingTimestamp(String dateFrom, String timestamp, String dbFilePath) {
    if ((dateFrom == null || dateFrom.isEmpty()) && (timestamp == null || timestamp.isEmpty())) {
      dateFrom = getDateForLimit(DateOption.MIN, dbFilePath);
      return Converter.convertStringToTimestamp(dateFrom, true);
    } else if ((dateFrom != null && !dateFrom.isEmpty()) && (timestamp == null || timestamp.isEmpty())) {
      return Converter.convertStringToTimestamp(dateFrom, true);
    } else {
      return new Timestamp(Long.parseLong(timestamp));
    }
  }

  private String getDateForLimit(DateOption dateOption, String dbFilePath) {
    return lookupRepository.getDateForLimit(dateOption, dbFilePath);
  }
}
