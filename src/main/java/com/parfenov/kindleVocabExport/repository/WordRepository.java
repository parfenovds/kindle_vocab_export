package com.parfenov.kindleVocabExport.repository;

import com.parfenov.kindleVocabExport.entity.User;
import com.parfenov.kindleVocabExport.entity.Word;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
  List<Word> findByUserId(Long userId);
  Optional<Word> findByWordAndUserId(String word, Long userId);

  Optional<Word> findByWordAndUser(String wordText, User user);
}
