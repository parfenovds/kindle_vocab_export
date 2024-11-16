package com.parfenov.kindleVocabExport.repository;

import com.parfenov.kindleVocabExport.constant.DateOption;
import com.parfenov.kindleVocabExport.entity.Lookup;
import com.parfenov.kindleVocabExport.service.SQLiteService;
import com.parfenov.kindleVocabExport.service.TempFileService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.sqlite.SQLiteDataSource;

class LookupRepositoryTest {

  @InjectMocks
  private LookupRepository lookupRepository;

  @Mock
  private SQLiteService sqliteService;

  @Mock
  private TempFileService tempFileService;

  private Path tempDbPath;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
    SQLiteDataSource dataSource = new SQLiteDataSource();
    tempDbPath = Path.of(System.getProperty("java.io.tmpdir"), "test-lookups.db");
    dataSource.setUrl("jdbc:sqlite:" + tempDbPath);
    Connection connection = dataSource.getConnection();
    try (Statement statement = connection.createStatement()) {
      statement.execute("DROP TABLE IF EXISTS lookups");
      statement.execute("""
                    CREATE TABLE lookups (
                        id TEXT PRIMARY KEY,
                        word_key TEXT,
                        book_key TEXT,
                        dict_key TEXT,
                        pos TEXT,
                        usage TEXT,
                        timestamp INTEGER
                    );
                    """);
      statement.execute("""
                    INSERT INTO lookups (id, word_key, book_key, dict_key, pos, usage, timestamp) VALUES
                        ('1', 'en:word1', 'book1', 'dict1', 'noun', 'Sample usage 1', 1441612800000),
                        ('2', 'en:word2', 'book2', 'dict2', 'verb', 'Sample usage 2', 1441785900000);
                    """);
    }
    when(tempFileService.getDatabasePath("testUser")).thenReturn(tempDbPath);
    lookupRepository = new LookupRepository(sqliteService, tempFileService);
    when(sqliteService.getConnection(tempDbPath.toString())).thenReturn(dataSource.getConnection());
  }

  @AfterEach
  void tearDown() throws Exception {
    if (tempDbPath != null) {
      Files.deleteIfExists(tempDbPath);
    }
  }

  @Test
  void testGetFiltered_shouldReturnFilteredLookups() {
    Timestamp from = new Timestamp(1441610000000L);
    Timestamp to = new Timestamp(1442006400000L);
    String sourceLanguage = "en";
    int limit = 2;

    Set<Lookup> lookups = lookupRepository.getFiltered("testUser", from, to, sourceLanguage, limit);

    assertEquals(2, lookups.size(), "Should return 2 lookups");
  }

  @Test
  void testGetDateForLimit_shouldReturnMaxDate() {
    String maxDate = lookupRepository.getDateForLimit(DateOption.MAX, "testUser");
    assertEquals("2015-09-09", maxDate, "Should return the maximum date");
  }

  @Test
  void testGetDateForLimit_shouldReturnMinDate() {
    String minDate = lookupRepository.getDateForLimit(DateOption.MIN, "testUser");
    assertEquals("2015-09-07", minDate, "Should return the minimum date");
  }
}
