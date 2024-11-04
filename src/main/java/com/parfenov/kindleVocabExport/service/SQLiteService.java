package com.parfenov.kindleVocabExport.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.stereotype.Service;

@Service
public class SQLiteService {

  public Connection getConnection(String dbFilePath) throws SQLException {
    String url = "jdbc:sqlite:" + dbFilePath;
    return DriverManager.getConnection(url);
  }
}
