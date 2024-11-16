package com.parfenov.kindleVocabExport.repository;

import com.parfenov.kindleVocabExport.entity.BookInfo;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@ExtendWith(MockitoExtension.class)
class BookRepositoryTest {

  @Mock
  private JdbcTemplate jdbcTemplate;

  @InjectMocks
  private BookRepository bookRepository;

  @Test
  void testGetAll_shouldReturnBookInfoList() {
    // Arrange
    List<BookInfo> expectedBooks = List.of(
        BookInfo.builder()
            .id("1")
            .asin("ASIN123")
            .guid("GUID123")
            .lang("en")
            .title("Book Title 1")
            .authors("Author 1")
            .build(),
        BookInfo.builder()
            .id("2")
            .asin("ASIN456")
            .guid("GUID456")
            .lang("fr")
            .title("Book Title 2")
            .authors("Author 2")
            .build()
    );

    RowMapper<BookInfo> mockRowMapper = (rs, rowNum) -> expectedBooks.get(rowNum - 1); // Simulate row mapping
    when(jdbcTemplate.query(eq("SELECT * FROM book_info;"), any(RowMapper.class))).thenReturn(expectedBooks);

    List<BookInfo> result = bookRepository.getAll();

    assertEquals(expectedBooks, result, "Should return the expected list of BookInfo");
    verify(jdbcTemplate).query(eq("SELECT * FROM book_info;"), any(RowMapper.class));
  }
}