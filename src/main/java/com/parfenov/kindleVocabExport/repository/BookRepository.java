
package com.parfenov.kindleVocabExport.repository;

import com.parfenov.kindleVocabExport.entity.basic.BookInfo;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {

  private final JdbcTemplate jdbcTemplate;
  private final RowMapper<BookInfo> rowMapper = (rs, rowNum) -> BookInfo.builder()
      .id(rs.getString("id"))
      .asin(rs.getString("asin"))
      .guid(rs.getString("guid"))
      .lang(rs.getString("lang"))
      .title(rs.getString("title"))
      .authors(rs.getString("authors"))
      .build();

  private static final String SELECT_BOOKINFO = "SELECT * FROM book_info;";

  public BookRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void setDatabasePath(String databasePath) {
    DataSource dataSource = DataSourceBuilder.create()
        .url("jdbc:sqlite:" + databasePath)
        .driverClassName("org.sqlite.JDBC")
        .build();
    this.jdbcTemplate.setDataSource(dataSource);
  }

  public List<BookInfo> getAll() {
    return jdbcTemplate.query(SELECT_BOOKINFO, rowMapper);
  }
}
