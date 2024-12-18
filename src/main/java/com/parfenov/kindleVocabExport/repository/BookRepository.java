package com.parfenov.kindleVocabExport.repository;

import com.parfenov.kindleVocabExport.entity.Book;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
  Optional<Book> findBySourceDbId(String sourceDbId);
}