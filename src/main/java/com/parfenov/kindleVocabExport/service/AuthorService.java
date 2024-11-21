package com.parfenov.kindleVocabExport.service;

import com.parfenov.kindleVocabExport.dto.AuthorDTO;
import com.parfenov.kindleVocabExport.dto.UserDTO;
import com.parfenov.kindleVocabExport.entity.Author;
import com.parfenov.kindleVocabExport.entity.User;
import com.parfenov.kindleVocabExport.exception.NotFoundException;
import com.parfenov.kindleVocabExport.mapper.AuthorMapper;
import com.parfenov.kindleVocabExport.mapper.UserMapper;
import com.parfenov.kindleVocabExport.repository.AuthorRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorService {
  private final AuthorRepository authorRepository;
  private final AuthorMapper authorMapper;
  private final UserService userService;
  private final UserMapper userMapper;

  public AuthorDTO createAuthor(AuthorDTO authorDTO) {
    UserDTO userDTO = userService.getCurrentUser();
    User user = userMapper.toEntity(userDTO);
    Author author = authorMapper.toEntity(authorDTO);
    author.setUser(user);
    Author savedAuthor = authorRepository.save(author);
    return authorMapper.toDTO(savedAuthor);
  }

  public AuthorDTO getAuthorById(Long id) {
    Author author = authorRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Author not found with ID: " + id));
    return authorMapper.toDTO(author);
  }

  public List<AuthorDTO> getAllAuthors() {
    return authorRepository.findAll().stream()
        .map(authorMapper::toDTO)
        .toList();
  }

  public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
    Author existingAuthor = authorRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Author not found with ID: " + id));

    User user = userService.getUserEntityById(authorDTO.getUserId());

    existingAuthor.setName(authorDTO.getName());
    existingAuthor.setChangedName(authorDTO.getChangedName());
    existingAuthor.setUser(user);

    Author updatedAuthor = authorRepository.save(existingAuthor);
    return authorMapper.toDTO(updatedAuthor);
  }

  public void deleteAuthor(Long id) {
    Author author = authorRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Author not found with ID: " + id));
    authorRepository.delete(author);
  }

  public Author getAuthorEntityById(Long id) {
    return authorRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Author not found with ID: " + id));
  }

  public Author findOrCreate(String authorName, User user) {
    return authorRepository.findByNameAndUser(authorName, user)
        .orElseGet(() -> {
          Author newAuthor = Author.builder()
              .name(authorName)
              .user(user)
              .build();
          return authorRepository.save(newAuthor);
        });
  }
}
