package com.parfenov.kindleVocabExport.mapper;

import com.parfenov.kindleVocabExport.dto.WordDTO;
import com.parfenov.kindleVocabExport.entity.Word;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WordMapper {
  WordDTO toDto(Word word);

  Word toEntity(WordDTO wordDTO);

  List<WordDTO> toDtoList(List<Word> words);

  List<Word> toEntityList(List<WordDTO> wordDTOs);
}
