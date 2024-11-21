package com.parfenov.kindleVocabExport.service;

import com.parfenov.kindleVocabExport.dto.WordDTO;
import com.parfenov.kindleVocabExport.entity.User;
import com.parfenov.kindleVocabExport.entity.Word;
import com.parfenov.kindleVocabExport.exception.NotFoundException;
import com.parfenov.kindleVocabExport.mapper.WordMapper;
import com.parfenov.kindleVocabExport.repository.WordRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WordService {
  private final WordRepository wordRepository;
  private final UserService userService;
  private final WordMapper wordMapper;

  @Transactional
  public WordDTO createWord(WordDTO wordDTO) {
    User user = userService.getUserEntityById(wordDTO.getUserId());
    Optional<WordDTO> optionalWord = findWordByNameAndUser(wordDTO.getWord(), user.getId());
    if (optionalWord.isPresent()) {
      return optionalWord.get();
    }
    Word word = wordMapper.toEntity(wordDTO);
    word.setUser(user);
    Word savedWord = wordRepository.save(word);
    return wordMapper.toDto(savedWord);
  }

  @Transactional
  public Word createEntityWord(WordDTO wordDTO) {
    User user = userService.getUserEntityById(wordDTO.getUserId());
    Optional<Word> optionalWord = findEntityWordByNameAndUser(wordDTO.getWord(), user.getId());
    if (optionalWord.isPresent()) {
      return optionalWord.get();
    }
    Word word = wordMapper.toEntity(wordDTO);
    word.setUser(user);
    return wordRepository.save(word);
  }

  @Transactional(readOnly = true)
  public WordDTO getWordById(Long id) {
    Word word = wordRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Word not found with ID: " + id));
    return wordMapper.toDto(word);
  }

  @Transactional(readOnly = true)
  public Word getEntityWordById(Long id) {
    return wordRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Word not found with ID: " + id));
  }

  @Transactional(readOnly = true)
  public List<WordDTO> getAllWordsForUser(Long userId) {
    List<Word> words = wordRepository.findByUserId(userId);
    return wordMapper.toDtoList(words);
  }

  @Transactional
  public WordDTO updateWord(Long id, WordDTO wordDTO) {
    Word existingWord = wordRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Word not found with ID: " + id));
    User user = userService.getUserEntityById(wordDTO.getUserId());

    existingWord.setWord(wordDTO.getWord());
    existingWord.setLanguage(wordDTO.getLanguage());
    existingWord.setUser(user);

    Word updatedWord = wordRepository.save(existingWord);
    return wordMapper.toDto(updatedWord);
  }

  @Transactional
  public void deleteWord(Long id) {
    Word existingWord = wordRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Word not found with ID: " + id));
    wordRepository.delete(existingWord);
  }

  @Transactional(readOnly = true)
  public Optional<WordDTO> findWordByNameAndUser(String word, Long userId) {
    return wordRepository.findByWordAndUserId(word, userId)
        .map(wordMapper::toDto);
  }

  @Transactional(readOnly = true)
  public Optional<Word> findEntityWordByNameAndUser(String word, Long userId) {
    return wordRepository.findByWordAndUserId(word, userId);
  }

  public Word findOrCreate(String wordText, String language, User user) {
    return wordRepository.findByWordAndUser(wordText, user)
        .orElseGet(() -> {
          Word newWord = new Word(wordText, language, user);
          return wordRepository.save(newWord);
        });
  }

  public void save(Word word) {
    wordRepository.save(word);
  }
}
