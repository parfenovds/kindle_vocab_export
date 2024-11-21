package com.parfenov.kindleVocabExport.service;

import com.parfenov.kindleVocabExport.dto.ContextDTO;
import com.parfenov.kindleVocabExport.entity.Book;
import com.parfenov.kindleVocabExport.entity.Context;
import com.parfenov.kindleVocabExport.exception.NotFoundException;
import com.parfenov.kindleVocabExport.mapper.ContextMapper;
import com.parfenov.kindleVocabExport.repository.ContextRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContextService {
  private final ContextRepository contextRepository;
  private final BookService bookService;
  private final ContextMapper contextMapper;

  public ContextDTO createContext(ContextDTO contextDTO) {
    Book book = bookService.getBookEntityById(contextDTO.getBookId());
    Context context = contextMapper.toEntity(contextDTO);
    context.setBook(book);
    Context savedContext = contextRepository.save(context);
    return contextMapper.toDTO(savedContext);
  }

  public Context createEntityContext(ContextDTO contextDTO) {
    Book book = bookService.getBookEntityById(contextDTO.getBookId());
    Context context = contextMapper.toEntity(contextDTO);
    context.setBook(book);
    return contextRepository.save(context);
  }

  public ContextDTO getContextById(Long id) {
    Context context = contextRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Context not found with ID: " + id));
    return contextMapper.toDTO(context);
  }

  public List<ContextDTO> getAllContexts() {
    return contextRepository.findAll().stream()
        .map(contextMapper::toDTO)
        .toList();
  }

//  public List<ContextDTO> getContextsByBookId(Long bookId) {
//    return contextRepository.findByBook_Id(bookId).stream()
//        .map(contextMapper::toDTO)
//        .toList();
//  }

  public ContextDTO updateContext(Long id, ContextDTO contextDTO) {
    Context existingContext = contextRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Context not found with ID: " + id));

    Book book = bookService.getBookEntityById(contextDTO.getBookId());
    existingContext.setOriginalSentence(contextDTO.getOriginalSentence());
    existingContext.setTranslatedSentence(contextDTO.getTranslatedSentence());
    existingContext.setBook(book);

    Context updatedContext = contextRepository.save(existingContext);
    return contextMapper.toDTO(updatedContext);
  }

  public void deleteContext(Long id) {
    Context existingContext = contextRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Context not found with ID: " + id));
    contextRepository.delete(existingContext);
  }

  public Context findOrCreate(String originalSentence, Book book) {
    return contextRepository.findByOriginalSentenceAndBook(originalSentence, book)
        .orElseGet(() -> {
          Context newContext = Context.builder()
              .originalSentence(originalSentence)
              .translatedSentence(null) // Оставляем пустым
              .book(book)
              .build();
          return contextRepository.save(newContext);
        });
  }
}
