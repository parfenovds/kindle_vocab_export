package com.parfenov.kindleVocabExport.service;

import com.parfenov.kindleVocabExport.entity.Book;
import com.parfenov.kindleVocabExport.entity.Context;
import com.parfenov.kindleVocabExport.entity.User;
import com.parfenov.kindleVocabExport.entity.Word;
import com.parfenov.kindleVocabExport.entity.basic.BookInfo;
import com.parfenov.kindleVocabExport.entity.basic.Lookup;
import com.parfenov.kindleVocabExport.mapper.WordMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DatabaseSavingService {
  private final TempFileService tempFileService;
  private final UserService userService;
  private final LookupService lookupService;
  private final BookInfoService bookInfoService;
  private final AuthorService authorService;
  private final BookService bookService;
  private final WordService wordService;
  private final ContextService contextService;
  private final WordMapper wordMapper;
  private final WordContextService wordContextService;

  @Transactional
  public void saveToDatabase(MultipartFile multipartFile) {
    User currentUser = userService.getCurrentUserEntity();
    tempFileService.uploadDatabaseFile(multipartFile, currentUser.getEmail());

    Set<Lookup> lookups = lookupService.getAll(currentUser.getEmail());
    List<BookInfo> bookInfoList = bookInfoService.getAll(currentUser.getEmail());

    Map<String, Book> books = new HashMap<>();
    for (BookInfo bookInfo : bookInfoList) {
      Book book = bookService.findOrCreateBySourceDbId(bookInfo.getId(), bookInfo, currentUser);
      books.put(bookInfo.getId(), book);
    }
    for (Lookup lookup : lookups) {
      String[] split = lookup.getWordKey().split(":");
      Word word = wordService.findOrCreate(split[1], split[0], currentUser);

      Context context = contextService.findOrCreate(lookup.getUsage(), books.get(lookup.getBookKey()));
      word.addContext(context, lookup.getTimestamp());
    }
  }
}
