
package com.parfenov.kindleVocabExport.repository;

import com.parfenov.kindleVocabExport.entity.basic.BookInfo;
import com.parfenov.kindleVocabExport.service.SQLiteService;
import com.parfenov.kindleVocabExport.service.TempFileService;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class BookInfoRepository {

  private final SQLiteService sqliteService;
  private final TempFileService tempFileService;

  public BookInfoRepository(SQLiteService sqliteService, TempFileService tempFileService) {
    this.sqliteService = sqliteService;
    this.tempFileService = tempFileService;
  }

  public List<BookInfo> getAll(String userKey) {
    List<BookInfo> bookInfos = new ArrayList<>();
    String query = """
        SELECT * FROM book_info;
        """;

    Path filePath = tempFileService.getDatabasePath(userKey);

    try (Connection connection = sqliteService.getConnection(filePath.toString());
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        bookInfos.add(mapResultSetToBookInfos(resultSet));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      tempFileService.deleteDatabaseFile(userKey);
    }
    return bookInfos;
  }

  private BookInfo mapResultSetToBookInfos(ResultSet resultSet) throws SQLException {
    return BookInfo.builder()
        .id(resultSet.getString("id"))
        .asin(resultSet.getString("asin"))
        .title(resultSet.getString("title"))
        .authors(resultSet.getString("authors"))
        .lang(resultSet.getString("lang"))
        .guid(resultSet.getString("guid"))
        .build();
  }
}
