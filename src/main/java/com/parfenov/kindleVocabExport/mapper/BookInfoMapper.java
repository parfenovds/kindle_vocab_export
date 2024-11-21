
package com.parfenov.kindleVocabExport.mapper;

import com.parfenov.kindleVocabExport.dto.BookInfoDTO;
import com.parfenov.kindleVocabExport.entity.basic.BookInfo;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BookInfoMapper implements BaseMapper<BookInfo, BookInfoDTO> {

  @Override
  public BookInfoDTO mapFrom(BookInfo source) {
    return BookInfoDTO.builder()
        .author(source.getAuthors())
        .title(source.getTitle())
        .build();
  }

  public List<BookInfoDTO> mapAll(List<BookInfo> bookInfoSet) {
    return bookInfoSet.stream().map(this::mapFrom).toList();
  }
}
