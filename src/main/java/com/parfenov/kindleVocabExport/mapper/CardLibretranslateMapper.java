package com.parfenov.kindleVocabExport.mapper;


import com.parfenov.kindleVocabExport.dto.CardLibretranslateDTO;
import com.parfenov.kindleVocabExport.entity.basic.Card;
import org.springframework.stereotype.Component;

@Component
public class CardLibretranslateMapper implements BaseMapper<Card, CardLibretranslateDTO> {

  @Override
  public CardLibretranslateDTO mapFrom(Card source) {
    return CardLibretranslateDTO.builder()
        .q(source.getOriginalSentence())
        .source(source.getSourceLanguage())
        .target(source.getTargetLanguage())
        .build();
  }
}
