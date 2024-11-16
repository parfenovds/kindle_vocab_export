package com.parfenov.kindleVocabExport.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

class TempFileServiceTest {

  private TempFileService tempFileService;

  @BeforeEach
  void setUp() throws IOException {
    tempFileService = new TempFileService();
  }

  @Test
  void uploadDatabaseFile_shouldStoreFileAndAddToUserFiles() throws IOException {
    MockMultipartFile mockFile = new MockMultipartFile(
        "file",
        "vocab.db",
        "application/octet-stream",
        "test content".getBytes()
    );
    String userKey = "testUser";
    tempFileService.uploadDatabaseFile(mockFile, userKey);
    Path storedFilePath = tempFileService.getDatabasePath(userKey);
    assertNotNull(storedFilePath, "The file path should be stored in the userFiles map.");
    assertTrue(Files.exists(storedFilePath), "The file should exist in the upload directory.");
    Files.deleteIfExists(storedFilePath);
  }

  @Test
  void getDatabasePath_shouldReturnCorrectPathForExistingUserKey() throws IOException {
    MockMultipartFile mockFile = new MockMultipartFile(
        "file",
        "vocab.db",
        "application/octet-stream",
        "test content".getBytes()
    );
    String userKey = "testUser";
    tempFileService.uploadDatabaseFile(mockFile, userKey);
    Path storedFilePath = tempFileService.getDatabasePath(userKey);
    assertNotNull(storedFilePath, "The file path should be returned for a valid userKey.");
    assertTrue(Files.exists(storedFilePath), "The file should exist in the upload directory.");
    Files.deleteIfExists(storedFilePath);
  }

  @Test
  void deleteDatabaseFile_shouldRemoveFileAndPathFromUserFiles() throws IOException {
    MockMultipartFile mockFile = new MockMultipartFile(
        "file",
        "vocab.db",
        "application/octet-stream",
        "test content".getBytes()
    );
    String userKey = "testUser";
    tempFileService.uploadDatabaseFile(mockFile, userKey);
    Path storedFilePath = tempFileService.getDatabasePath(userKey);
    assertNotNull(storedFilePath, "The file should be stored initially.");
    assertTrue(Files.exists(storedFilePath), "The file should exist before deletion.");
    tempFileService.deleteDatabaseFile(userKey);
    assertFalse(Files.exists(storedFilePath), "The file should be deleted.");
    assertNull(tempFileService.getDatabasePath(userKey), "The userFiles map should not contain the userKey after deletion.");
  }

  @Test
  void deleteDatabaseFile_shouldNotThrowExceptionIfFileDoesNotExist() {
    String userKey = "nonExistingUser";
    assertDoesNotThrow(() -> tempFileService.deleteDatabaseFile(userKey), "Deleting a non-existing file should not throw an exception.");
  }
}