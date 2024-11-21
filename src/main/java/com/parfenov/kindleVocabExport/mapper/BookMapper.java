package com.parfenov.kindleVocabExport.mapper;

import com.parfenov.kindleVocabExport.dto.BookDTO;
import com.parfenov.kindleVocabExport.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public interface BookMapper {
  @Mapping(source = "author.id", target = "authorId")
  BookDTO toDTO(Book book);

  @Mapping(source = "authorId", target = "author.id")
  Book toEntity(BookDTO bookDTO);
}