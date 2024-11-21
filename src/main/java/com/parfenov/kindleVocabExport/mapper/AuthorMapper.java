package com.parfenov.kindleVocabExport.mapper;

import com.parfenov.kindleVocabExport.dto.AuthorDTO;
import com.parfenov.kindleVocabExport.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
  @Mapping(source = "user.id", target = "userId")
  AuthorDTO toDTO(Author author);

  @Mapping(source = "userId", target = "user.id")
  Author toEntity(AuthorDTO authorDTO);
}
