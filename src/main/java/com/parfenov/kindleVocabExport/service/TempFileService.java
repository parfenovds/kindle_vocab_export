package com.parfenov.kindleVocabExport.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TempFileService {

  private static final String UPLOAD_DIRECTORY = System.getProperty("java.io.tmpdir") + "/uploads";
  private final ConcurrentHashMap<String, Path> userFiles = new ConcurrentHashMap<>();

  public TempFileService() throws IOException {
    Files.createDirectories(Paths.get(UPLOAD_DIRECTORY));
  }

  public Path uploadDatabaseFile(MultipartFile file, String userKey) throws IOException {
    String uniqueFileName = "vocab-" + userKey + ".db";
    Path filePath = Paths.get(UPLOAD_DIRECTORY, uniqueFileName);
    file.transferTo(filePath.toFile());
    userFiles.put(userKey, filePath);
    return filePath;
  }

  public Path getDatabasePath(String userKey) {
    return userFiles.get(userKey);
  }

  public void deleteDatabaseFile(String userKey) {
    Path filePath = userFiles.remove(userKey);
    if (filePath != null && Files.exists(filePath)) {
      try {
        Files.delete(filePath);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
