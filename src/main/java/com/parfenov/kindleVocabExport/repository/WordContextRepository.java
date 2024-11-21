package com.parfenov.kindleVocabExport.repository;

import com.parfenov.kindleVocabExport.entity.Context;
import com.parfenov.kindleVocabExport.entity.Word;
import com.parfenov.kindleVocabExport.entity.WordContext;
import com.parfenov.kindleVocabExport.entity.WordContextId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordContextRepository extends JpaRepository<WordContext, WordContextId> {

  Optional<WordContext> findByWordAndContext(Word word, Context context);

  List<WordContext> findByWord(Word word);

  List<WordContext> findByContext(Context context);
}