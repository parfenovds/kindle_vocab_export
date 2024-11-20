package com.parfenov.kindleVocabExport.mapper;

import com.parfenov.kindleVocabExport.entity.basic.Card;
import com.parfenov.kindleVocabExport.entity.basic.Lookup;
import java.util.HashSet;
import org.springframework.stereotype.Component;

@Component
public class LookupCardMapper implements BaseMapper<Lookup, Card> {

  @Override
  public Card mapFrom(Lookup source) {
    String[] langToWord = source.getWordKey().split(":");
    return Card.builder()
        .words(new HashSet<>())
        .sourceLanguage(langToWord[0])
        .originalSentence(source.getUsage().replaceAll("\u00A0", "").trim())
        .timestamp(source.getTimestamp())
        .build();
  }
}
