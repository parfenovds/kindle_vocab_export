package com.parfenov.kindleVocabExport.service;

import com.parfenov.kindleVocabExport.entity.basic.BookInfo;
import com.parfenov.kindleVocabExport.repository.BookInfoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookInfoService {
  private final BookInfoRepository bookInfoRepository;

  public List<BookInfo> getAll(String userKey) {
    return bookInfoRepository.getAll(userKey);
  }
}
