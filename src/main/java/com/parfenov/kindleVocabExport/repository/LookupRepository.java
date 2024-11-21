package com.parfenov.kindleVocabExport.repository;

import com.parfenov.kindleVocabExport.constant.DateOption;
import com.parfenov.kindleVocabExport.entity.basic.Lookup;
import com.parfenov.kindleVocabExport.service.SQLiteService;
import com.parfenov.kindleVocabExport.service.TempFileService;
import com.parfenov.kindleVocabExport.util.Converter;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class LookupRepository {

  private final SQLiteService sqliteService;
  private final TempFileService tempFileService;

  public LookupRepository(SQLiteService sqliteService, TempFileService tempFileService) {
    this.sqliteService = sqliteService;
    this.tempFileService = tempFileService;
  }

  public Set<Lookup> getFiltered(String userKey, Timestamp timestampFrom, Timestamp timestampTo, String sourceLanguage, Integer limit) {
    Set<Lookup> lookups = new HashSet<>();
    String query = """
                SELECT * FROM lookups
                WHERE timestamp BETWEEN ? AND ?
                AND word_key LIKE ?
                LIMIT ?;
                """;

    Path filePath = tempFileService.getDatabasePath(userKey);

    try (Connection connection = sqliteService.getConnection(filePath.toString());
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

      preparedStatement.setTimestamp(1, timestampFrom);
      preparedStatement.setTimestamp(2, timestampTo);
      preparedStatement.setString(3, sourceLanguage == null ? "%" : (sourceLanguage + ":%"));
      preparedStatement.setInt(4, limit);

      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        lookups.add(mapResultSetToLookup(resultSet));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      tempFileService.deleteDatabaseFile(userKey);
    }

    return lookups;
  }

  public Set<Lookup> getAll(String userKey) {
    Set<Lookup> lookups = new HashSet<>();
    String query = """
                SELECT * FROM lookups;
                """;

    Path filePath = tempFileService.getDatabasePath(userKey);

    try (Connection connection = sqliteService.getConnection(filePath.toString());
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        lookups.add(mapResultSetToLookup(resultSet));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
//      tempFileService.deleteDatabaseFile(userKey);
    }
    return lookups;
  }

  public String getDateForLimit(DateOption dateOption, String userKey) {
    String query = dateOption == DateOption.MIN
        ? "SELECT MIN(timestamp) AS timestamp FROM lookups"
        : "SELECT MAX(timestamp) AS timestamp FROM lookups";

    Path filePath = tempFileService.getDatabasePath(userKey);

    try (Connection connection = sqliteService.getConnection(filePath.toString());
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {

      if (resultSet.next()) {
        Timestamp timestamp = resultSet.getTimestamp("timestamp");
        return timestamp != null ? Converter.convertTimestampToString(timestamp) : null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  private Lookup mapResultSetToLookup(ResultSet resultSet) throws SQLException {
    return Lookup.builder()
        .id(resultSet.getString("id"))
        .wordKey(resultSet.getString("word_key"))
        .bookKey(resultSet.getString("book_key"))
        .dictKey(resultSet.getString("dict_key"))
        .pos(resultSet.getString("pos"))
        .usage(resultSet.getString("usage"))
        .timestamp(resultSet.getTimestamp("timestamp"))
        .build();
  }
}
