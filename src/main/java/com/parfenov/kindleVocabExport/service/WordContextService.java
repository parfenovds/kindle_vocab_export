package com.parfenov.kindleVocabExport.service;

import com.parfenov.kindleVocabExport.entity.Context;
import com.parfenov.kindleVocabExport.entity.Word;
import com.parfenov.kindleVocabExport.entity.WordContext;
import com.parfenov.kindleVocabExport.repository.WordContextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WordContextService {
  private final WordContextRepository wordContextRepository;

  public WordContext findOrCreate(Word word, Context context) {
    return wordContextRepository.findByWordAndContext(word, context)
        .orElseGet(() -> {
          WordContext wordContext = new WordContext(word, context);
          return wordContextRepository.save(wordContext);
        });
  }
}
