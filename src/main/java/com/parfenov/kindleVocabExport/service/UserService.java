package com.parfenov.kindleVocabExport.service;

import com.parfenov.kindleVocabExport.dto.RegisterDTO;
import com.parfenov.kindleVocabExport.dto.UserDTO;
import com.parfenov.kindleVocabExport.entity.User;
import com.parfenov.kindleVocabExport.exception.NotFoundException;
import com.parfenov.kindleVocabExport.mapper.UserMapper;
import com.parfenov.kindleVocabExport.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  public void registerUser(RegisterDTO registerDTO) {
    if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
      throw new IllegalArgumentException("Password and Confirm Password do not match");
    }

    userRepository.findByEmail(registerDTO.getEmail())
        .ifPresent(user -> {
          throw new IllegalArgumentException("Email address is already used");
        });

    User newUser = User.builder()
        .email(registerDTO.getEmail())
        .password(passwordEncoder.encode(registerDTO.getPassword()))
        .build();

    userRepository.save(newUser);
  }

  @Transactional(readOnly = true)
  public UserDTO getCurrentUser() {
    String currentUsername = getCurrentUsername();
    User user = userRepository.findByEmail(currentUsername)
        .orElseThrow(() -> new NotFoundException("User not found with email: " + currentUsername));
    return userMapper.toDto(user);
  }

  private String getCurrentUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  public User getUserEntityById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
  }

  public User getCurrentUserEntity() {
    String currentUsername = getCurrentUsername();
    return userRepository.findByEmail(currentUsername)
        .orElseThrow(() -> new NotFoundException("User not found with email: " + currentUsername));
  }
}
