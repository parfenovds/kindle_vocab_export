package com.parfenov.kindleVocabExport.repository;

import com.parfenov.kindleVocabExport.entity.Book;
import com.parfenov.kindleVocabExport.entity.Context;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContextRepository extends JpaRepository<Context, Long> {
  Optional<Context> findByOriginalSentenceAndBook(String originalSentence, Book book);
}
