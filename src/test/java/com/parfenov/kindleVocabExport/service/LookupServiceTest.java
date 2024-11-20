package com.parfenov.kindleVocabExport.service;

import com.parfenov.kindleVocabExport.constant.DateOption;
import com.parfenov.kindleVocabExport.entity.basic.Lookup;
import com.parfenov.kindleVocabExport.repository.LookupRepository;
import com.parfenov.kindleVocabExport.util.Converter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

class LookupServiceTest {

  @Mock
  private LookupRepository lookupRepository;

  @InjectMocks
  private LookupService lookupService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetFiltered_shouldReturnFilteredLookups() {
    String dbFilePath = "test-db-path";
    String dateFrom = "2015-09-07";
    String dateTo = "2015-09-09";
    String sourceLanguage = "en";
    Integer limit = 10;
    String timestamp = null;

    Timestamp expectedDateFrom = Converter.convertStringToTimestamp(dateFrom, true);
    Timestamp expectedDateTo = Converter.convertStringToTimestamp(dateTo, false);

    Set<Lookup> mockLookups = new HashSet<>();
    mockLookups.add(new Lookup("1", "en:word1", "book1", "dict1", "noun", "Sample usage 1", expectedDateFrom));
    mockLookups.add(new Lookup("2", "en:word2", "book2", "dict2", "verb", "Sample usage 2", expectedDateTo));

    when(lookupRepository.getFiltered(
        eq(dbFilePath),
        eq(expectedDateFrom),
        eq(expectedDateTo),
        eq(sourceLanguage),
        eq(limit)
    )).thenReturn(mockLookups);

    Set<Lookup> lookups = lookupService.getFiltered(dbFilePath, dateFrom, dateTo, sourceLanguage, limit, timestamp);

    assertEquals(2, lookups.size(), "Should return 2 lookups");
    verify(lookupRepository, times(1)).getFiltered(
        eq(dbFilePath),
        eq(expectedDateFrom),
        eq(expectedDateTo),
        eq(sourceLanguage),
        eq(limit)
    );
  }

  @Test
  void testGetFiltered_shouldUseDefaultDateToAndLimit() {
    String dbFilePath = "test-db-path";
    String dateFrom = "2015-09-07";
    String dateTo = null;
    String sourceLanguage = "en";
    Integer limit = null;
    String timestamp = null;

    String maxDate = "2015-09-09";
    when(lookupRepository.getDateForLimit(DateOption.MAX, dbFilePath)).thenReturn(maxDate);

    Timestamp expectedDateFrom = Converter.convertStringToTimestamp(dateFrom, true);
    Timestamp expectedDateTo = Converter.convertStringToTimestamp(maxDate, false);

    Set<Lookup> mockLookups = new HashSet<>();
    mockLookups.add(new Lookup("1", "en:word1", "book1", "dict1", "noun", "Sample usage 1", expectedDateFrom));
    mockLookups.add(new Lookup("2", "en:word2", "book2", "dict2", "verb", "Sample usage 2", expectedDateTo));

    when(lookupRepository.getFiltered(
        eq(dbFilePath),
        eq(expectedDateFrom),
        eq(expectedDateTo),
        eq(sourceLanguage),
        eq(Integer.MAX_VALUE)
    )).thenReturn(mockLookups);

    Set<Lookup> lookups = lookupService.getFiltered(dbFilePath, dateFrom, dateTo, sourceLanguage, limit, timestamp);

    assertEquals(2, lookups.size(), "Should return 2 lookups");
    verify(lookupRepository, times(1)).getFiltered(
        eq(dbFilePath),
        eq(expectedDateFrom),
        eq(expectedDateTo),
        eq(sourceLanguage),
        eq(Integer.MAX_VALUE)
    );
  }

  @Test
  void testGetStartingTimestamp_shouldUseTimestampIfProvided() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    String timestamp = "1441612800000"; // 2015-09-07 10:00:00 in milliseconds
    String dateFrom = null;
    String dbFilePath = "test-db-path";

    Timestamp expectedTimestamp = new Timestamp(Long.parseLong(timestamp));

    Method method = LookupService.class.getDeclaredMethod("getStartingTimestamp", String.class, String.class, String.class);
    method.setAccessible(true);

    Timestamp result = (Timestamp) method.invoke(lookupService, dateFrom, timestamp, dbFilePath);

    assertEquals(expectedTimestamp, result, "Should return timestamp from input");
  }

  @Test
  void testGetStartingTimestamp_shouldReturnMinDate() throws Exception {
    String timestamp = null;
    String dateFrom = null;
    String dbFilePath = "test-db-path";
    String minDate = "2015-09-07";

    when(lookupRepository.getDateForLimit(DateOption.MIN, dbFilePath)).thenReturn(minDate);

    Timestamp expectedTimestamp = Converter.convertStringToTimestamp(minDate, true);

    Method method = LookupService.class.getDeclaredMethod("getStartingTimestamp", String.class, String.class, String.class);
    method.setAccessible(true);

    Timestamp result = (Timestamp) method.invoke(lookupService, dateFrom, timestamp, dbFilePath);

    assertEquals(expectedTimestamp, result, "Should return minimum date from repository");
  }
}
