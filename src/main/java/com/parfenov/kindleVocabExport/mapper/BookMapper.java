
package com.parfenov.kindleVocabExport.mapper;

import com.parfenov.kindleVocabExport.dto.BookDTO;
import com.parfenov.kindleVocabExport.entity.basic.BookInfo;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements BaseMapper<BookInfo, BookDTO> {

  @Override
  public BookDTO mapFrom(BookInfo source) {
    return BookDTO.builder()
        .author(source.getAuthors())
        .title(source.getTitle())
        .build();
  }

  public List<BookDTO> mapAll(List<BookInfo> bookInfoSet) {
    return bookInfoSet.stream().map(this::mapFrom).toList();
  }
}
