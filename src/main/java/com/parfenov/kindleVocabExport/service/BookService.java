package com.parfenov.kindleVocabExport.service;

import com.parfenov.kindleVocabExport.dto.BookDTO;
import com.parfenov.kindleVocabExport.entity.Author;
import com.parfenov.kindleVocabExport.entity.Book;
import com.parfenov.kindleVocabExport.entity.User;
import com.parfenov.kindleVocabExport.entity.basic.BookInfo;
import com.parfenov.kindleVocabExport.exception.NotFoundException;
import com.parfenov.kindleVocabExport.mapper.BookMapper;
import com.parfenov.kindleVocabExport.repository.BookRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {
  private final BookRepository bookRepository;
  private final AuthorService authorService;
  private final BookMapper bookMapper;

  public BookDTO createBook(BookDTO bookDTO) {
    Author author = authorService.getAuthorEntityById(bookDTO.getAuthorId());
    Book book = bookMapper.toEntity(bookDTO);
    book.setAuthor(author);

    Book savedBook = bookRepository.save(book);
    return bookMapper.toDTO(savedBook);
  }

  public BookDTO getBookById(Long id) {
    Book book = bookRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Book not found with ID: " + id));
    return bookMapper.toDTO(book);
  }

  public List<BookDTO> getAllBooks() {
    return bookRepository.findAll().stream()
        .map(bookMapper::toDTO)
        .toList();
  }

  public BookDTO updateBook(Long id, BookDTO bookDTO) {
    Book existingBook = bookRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Book not found with ID: " + id));

    Author author = authorService.getAuthorEntityById(bookDTO.getAuthorId());
    Book updatedBook = bookMapper.toEntity(bookDTO);
    updatedBook.setId(existingBook.getId());
    updatedBook.setAuthor(author);

    Book savedBook = bookRepository.save(updatedBook);
    return bookMapper.toDTO(savedBook);
  }

  public void deleteBook(Long id) {
    Book existingBook = bookRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Book not found with ID: " + id));
    bookRepository.delete(existingBook);
  }

  public Book getBookEntityById(Long bookId) {
    return bookRepository.findById(bookId)
        .orElseThrow(() -> new NotFoundException("Book not found with ID: " + bookId));
  }

  public Book findOrCreateBySourceDbId(String sourceDbId, BookInfo bookInfo, User user) {
    return bookRepository.findBySourceDbId(sourceDbId)
        .orElseGet(() -> {
          Author author = authorService.findOrCreate(bookInfo.getAuthors(), user);

          Book newBook = Book.builder()
              .sourceDbId(sourceDbId)
              .title(bookInfo.getTitle())
              .changedTitle(null)
              .language(bookInfo.getLang())
              .author(author)
              .build();

          return bookRepository.save(newBook);
        });
  }
}
