package com.parfenov.kindleVocabExport.repository;

import com.parfenov.kindleVocabExport.entity.Author;
import com.parfenov.kindleVocabExport.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
  Optional<Author> findByNameAndUser(String authorName, User user);
}
