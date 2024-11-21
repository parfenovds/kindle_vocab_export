package com.parfenov.kindleVocabExport.mapper;

import com.parfenov.kindleVocabExport.dto.ContextDTO;
import com.parfenov.kindleVocabExport.entity.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BookMapper.class})
public interface ContextMapper {
  @Mapping(source = "book.id", target = "bookId")
  ContextDTO toDTO(Context context);

  @Mapping(source = "bookId", target = "book.id")
  Context toEntity(ContextDTO contextDTO);
}
